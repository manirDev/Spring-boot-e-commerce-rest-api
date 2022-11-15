package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.CartItem;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCustomer(User customer);

    //CartItem findByCustomerAndProduct(User customer, Product product);
    CartItem findByCustomerAndProduct(User customer, Product product);

    @Query("UPDATE CartItem c SET c.quantity = ?3 WHERE c.product.id = ?2 AND c.customer.id = ?1")
    void updateItemQuantity(Long customerId, Long productId, Integer quantity);

    @Query("DELETE FROM CartItem c WHERE c.customer.id = ?1 AND c.product.id = ?2")
    @Modifying
    void deleteByCustomerAndProduct(Long customerId, Long productId);

    @Query("DELETE FROM CartItem c WHERE c.customer.id = ?1")
    @Modifying
    void deleteByCustomerId(Long customerId);

}
