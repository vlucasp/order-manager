package br.com.ordermanager.service;

import br.com.ordermanager.service.helper.NegocioExceptionHelper;
import br.com.ordermanager.service.vo.OrderVO;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

public interface ProductOrderService {

    @Transactional
    void createOrders(List<OrderVO> pedidos) throws NegocioExceptionHelper;

    List<OrderVO> findOrderByFilter(String orderNumber, Date registryDate) throws NegocioExceptionHelper;
}
