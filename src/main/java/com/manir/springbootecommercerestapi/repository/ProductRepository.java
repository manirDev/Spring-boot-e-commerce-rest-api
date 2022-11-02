package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.resource.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
