package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.response.CartItemResponse;

public interface ShoppingCartService {

    CartItemResponse findByCustomer(User customer);

    CartItemResponse addCartItem(User customer, Long productId, Integer quantity);

    CartItemResponse updateItemQuantity(User customer, Long productId, Integer quantity);

    void deleteItemProduct(User customer, Long productId);
}
