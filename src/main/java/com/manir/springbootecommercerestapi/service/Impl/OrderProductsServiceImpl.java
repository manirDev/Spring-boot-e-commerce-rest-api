package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.model.OrderProducts;
import com.manir.springbootecommercerestapi.repository.OrderProductsRepository;
import com.manir.springbootecommercerestapi.service.OrderProductsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@AllArgsConstructor
public class OrderProductsServiceImpl implements OrderProductsService {

    @Resource(name = "orderProductsRepository")
    private final OrderProductsRepository orderProductsRepository;

    @Override
    public void addOrderProducts(OrderProducts orderProducts) {
        orderProductsRepository.save(orderProducts);
    }
}
