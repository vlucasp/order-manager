package br.com.ordermanager.service.impl;

import br.com.ordermanager.db.model.Customer;
import br.com.ordermanager.db.repository.CustomerRepository;
import br.com.ordermanager.service.CustomerService;
import br.com.ordermanager.service.helper.NegocioExceptionHelper;
import br.com.ordermanager.service.impl.commom.AbstractService;
import br.com.ordermanager.service.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl extends AbstractService implements CustomerService {

    private static final String CUSTOMER = "Cliente";
    private static final String[] CUSTOMER_PROPERTIES = { "id", "name", "email" };

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerVO> findAllCustomer() throws NegocioExceptionHelper {
        try {
            List<Customer> customers = customerRepository.findAll();

            if (customers.isEmpty()) {
                throw new NegocioExceptionHelper(emptyResponseMessage());
            }

            return customers.stream().map(CustomerVO::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CustomerVO findCustomerById(Long id) throws NegocioExceptionHelper {
        try {
            Customer customer = findById(id);
            return new CustomerVO(customer);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CustomerVO createCustomer(CustomerVO vo) {
        try {
            Customer customer = customerRepository.save(new Customer(vo));
            return new CustomerVO(customer);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public CustomerVO updateCustomer(CustomerVO vo) throws NegocioExceptionHelper {
        try {
            Customer customer = findById(vo.getId());
            copyProperties(vo, customer, CUSTOMER_PROPERTIES);
            customerRepository.save(customer);
            return new CustomerVO(customer);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteCustomerById(Long id) throws NegocioExceptionHelper {
        try {
            Customer customer = findById(id);
            customerRepository.delete(customer);
        } catch (Exception e) {
            throw e;
        }
    }

    private Customer findById(Long id) throws NegocioExceptionHelper {
        return customerRepository.findById(id).orElseThrow(
                () -> new NegocioExceptionHelper(objectNotFoundMessage(CUSTOMER))
        );
    }

}
