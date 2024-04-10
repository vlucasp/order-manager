package br.com.ordermanager.service;

import br.com.ordermanager.service.helper.NegocioExceptionHelper;
import br.com.ordermanager.service.vo.CustomerVO;

import java.util.List;

public interface CustomerService {
    List<CustomerVO> findAllCustomer() throws NegocioExceptionHelper;

    CustomerVO findCustomerById(Long id) throws NegocioExceptionHelper;

    CustomerVO createCustomer(CustomerVO vo);

    CustomerVO updateCustomer(CustomerVO vo) throws NegocioExceptionHelper;

    void deleteCustomerById(Long id) throws NegocioExceptionHelper;
}
