package br.com.ordermanager.controller;

import br.com.ordermanager.controller.commom.AbstractController;
import br.com.ordermanager.controller.dto.CustomerDTO;
import br.com.ordermanager.service.helper.NegocioExceptionHelper;
import br.com.ordermanager.service.CustomerService;
import br.com.ordermanager.service.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

    private static final String CUSTOMER = "Cliente";

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Object> findAllCustomer() {
        try {
            List<CustomerVO> vos = customerService.findAllCustomer();

            return buildSuccessResponse(vos.stream()
                    .map(CustomerDTO::new)
                    .collect(Collectors.toList()));
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findCustomerById(@PathVariable Long id) {
        try {
            CustomerVO vo = customerService.findCustomerById(id);
            return buildSuccessResponse(new CustomerDTO(vo));
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO dto) {
        try {
            CustomerVO vo = customerService.createCustomer(new CustomerVO(dto));
            return buildCreateSuccessResponse(vo, CUSTOMER);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDTO dto) {
        try {
            if (dto.getId() == null) {
                return buildRequestWithoutParameterResponse("Id");
            }
            CustomerVO vo = customerService.updateCustomer(new CustomerVO(dto));
            return buildUpdateSuccessResponse(new CustomerDTO(vo), CUSTOMER);
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomerById(id);
            return buildDeleteSuccessResponse(CUSTOMER);
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }
}
