package br.com.ordermanager.service.impl;

import br.com.ordermanager.db.model.Customer;
import br.com.ordermanager.db.model.ProductOrder;
import br.com.ordermanager.db.repository.CustomerRepository;
import br.com.ordermanager.db.repository.ProductOrderRepository;
import br.com.ordermanager.service.ProductOrderService;
import br.com.ordermanager.service.helper.NegocioExceptionHelper;
import br.com.ordermanager.service.impl.commom.AbstractService;
import br.com.ordermanager.service.vo.OrderVO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
public class ProductOrderServiceImpl extends AbstractService implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final String CUSTOMER = "Cliente";

    @Override
    @Transactional
    public void createOrders(List<OrderVO> pedidos) throws NegocioExceptionHelper {
        try {
            for (OrderVO order : pedidos) {
                Customer customer = customerRepository.findById(order.getCodigo_cliente()).orElseThrow(
                        () -> new NegocioExceptionHelper(objectNotFoundMessage(CUSTOMER))
                );

                ProductOrder productOrder = productOrderRepository.findDistinctFirstByOrderNumber(order.getNumero_controle());
                if (productOrder != null)
                    throw new NegocioExceptionHelper(format("Já existe pedido com o mesmo número de controle {0}.", order.getNumero_controle()));

                productOrderRepository.save(createProductOrder(order, customer));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<OrderVO> findOrderByFilter(String orderNumber, Date registryDate) throws NegocioExceptionHelper {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<ProductOrder> productOrders = productOrderRepository.findProductOrderByFilters(orderNumber, registryDate != null ? formatter.format(registryDate) : null);

        if (productOrders.isEmpty())
            throw new NegocioExceptionHelper(emptyResponseMessage());

        return productOrders.stream()
                .map(OrderVO::new)
                .collect(Collectors.toList());
    }

    private ProductOrder createProductOrder(OrderVO order, Customer customer) {
        ProductOrder productOrder = new ProductOrder();

        Integer quantity = order.getQuantidade() == null ? 1 : order.getQuantidade();
        BigDecimal price = order.getValor().multiply(new BigDecimal(quantity));

        if (quantity >= 10) {
            productOrder.setTotalPrice(applyDiscount(price, new BigDecimal("10")));
        } else if (quantity >= 5) {
            productOrder.setTotalPrice(applyDiscount(price, new BigDecimal("5")));
        } else {
            productOrder.setTotalPrice(price);
        }

        productOrder.setOrderNumber(order.getNumero_controle());
        productOrder.setRegistryDate(order.getData_cadastro() == null ? new Date() : order.getData_cadastro());
        productOrder.setProductName(order.getNome());
        productOrder.setPrice(order.getValor());
        productOrder.setCustomer(customer);
        productOrder.setQuantity(quantity);
        return productOrder;
    }

    public static BigDecimal applyDiscount(BigDecimal price, BigDecimal descount) {
        BigDecimal discountFactor = BigDecimal.ONE.subtract(descount.divide(BigDecimal.valueOf(100)));
        return price.multiply(discountFactor);
    }
}
