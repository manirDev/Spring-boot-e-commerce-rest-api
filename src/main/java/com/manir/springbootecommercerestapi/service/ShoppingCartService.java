package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.response.CartItemResponse;

public interface ShoppingCartService {

    CartItemResponse findByCustomerId(Long customerId);

    CartItemResponse addCartItem(Long customerId, Long productId, Integer quantity);

    CartItemResponse updateItemQuantity(Long customerId, Long productId, Integer quantity);

    void deleteItemProduct(Long customerId, Long productId);
}
