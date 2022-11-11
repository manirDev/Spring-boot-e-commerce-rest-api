package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCustomerId(Long customerId);

    //CartItem findByCustomerAndProduct(Customer customer, Product product);
    CartItem findByCustomerIdAndProductId(Long customerId, Long productId);

}
