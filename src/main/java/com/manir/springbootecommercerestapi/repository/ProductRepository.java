package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
