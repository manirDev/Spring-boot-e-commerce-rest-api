package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.OrderProducts;
import com.manir.springbootecommercerestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {
    List<OrderProducts> findByCustomer(User customer);
}
