package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.OrderProductsDto;
import com.manir.springbootecommercerestapi.model.OrderProducts;
import com.manir.springbootecommercerestapi.model.User;

import java.util.List;

public interface OrderProductsService {
    void addOrderProducts(OrderProducts orderProducts);
    List<OrderProductsDto> findOrderItemsByCustomer(User customer);
}
