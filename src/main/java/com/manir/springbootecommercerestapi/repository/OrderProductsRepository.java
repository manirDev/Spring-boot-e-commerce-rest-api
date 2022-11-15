package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {

}
