package br.com.ordermanager.controller;

import br.com.ordermanager.controller.commom.AbstractController;
import br.com.ordermanager.controller.dto.OrderDTO;
import br.com.ordermanager.controller.dto.OrderFilterDTO;
import br.com.ordermanager.controller.dto.OrderListDTO;
import br.com.ordermanager.controller.dto.ResponseDTO;
import br.com.ordermanager.service.ProductOrderService;
import br.com.ordermanager.service.helper.NegocioExceptionHelper;
import br.com.ordermanager.service.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class ProductOrderController extends AbstractController {

    @Autowired
    private ProductOrderService productOrderService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> createProductOrder(@RequestBody OrderListDTO dto) {
        try {
            checkRequiredFields(dto);
            productOrderService.createOrders(dto.getPedidos().stream().map(OrderVO::new).collect(Collectors.toList()));
            return ResponseEntity.ok()
                    .body(
                            ResponseDTO.builder()
                                    .status(HttpStatus.CREATED.value())
                                    .message("Pedidos registrados com sucesso")
                                    .build()
                    );
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    private void checkRequiredFields(OrderListDTO dto) throws NegocioExceptionHelper {
        if (dto.getPedidos() == null || dto.getPedidos().isEmpty())
            throw new NegocioExceptionHelper("A lista de pedidos não pode ser vazia.");
        for (OrderDTO order : dto.getPedidos()) {
            if (order.getNumero_controle() == null)
                throw new NegocioExceptionHelper(buildRequiredFieldMessage("Número de controle"));
            if (order.getNome() == null)
                throw new NegocioExceptionHelper(buildRequiredFieldMessage("Nome"));
            if (order.getValor() == null)
                throw new NegocioExceptionHelper(buildRequiredFieldMessage("Valor"));
            if (order.getCodigo_cliente() == null)
                throw new NegocioExceptionHelper(buildRequiredFieldMessage("Código do cliente"));
        }
    }

    @GetMapping
    public ResponseEntity<Object> findOrders(@RequestBody OrderFilterDTO dto) {
        try {
            List<OrderVO> vos = productOrderService.findOrderByFilter(dto.getNumero_pedido(), dto.getData_cadastro());

            return buildSuccessResponse(vos.stream()
                    .map(OrderDTO::new)
                    .collect(Collectors.toList()));
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }
}
