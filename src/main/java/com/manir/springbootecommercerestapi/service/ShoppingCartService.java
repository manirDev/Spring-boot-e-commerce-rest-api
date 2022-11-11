package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.CartItemDto;

import java.util.List;

public interface ShoppingCartService {

    List<CartItemDto> findByCustomerId(Long customerId);

    CartItemDto addCartItem(Long customerId, Long productId, Integer quantity);
}
