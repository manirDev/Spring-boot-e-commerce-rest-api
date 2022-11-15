package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.OrderDto;
import com.manir.springbootecommercerestapi.model.User;

import java.util.List;

public interface OrderService {

    void placeOrder(User customer);
    OrderDto saveOrder(OrderDto orderDto, User customer);
    List<OrderDto> listOrdersByCustomer(User customer);
}
