package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Order;
import com.manir.springbootecommercerestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(User customer);
}
