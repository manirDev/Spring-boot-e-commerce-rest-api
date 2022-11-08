package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.resource.Comment;
import com.manir.springbootecommercerestapi.resource.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
