package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            "SELECT p FROM Product p WHERE " +
            "p.title LIKE CONCAT('%', :query, '%') " +
            "or p.description LIKE CONCAT('%', :query, '%')" +
            "or p.keywords LIKE CONCAT('%', :query, '%')" +
            "or p.detail LIKE CONCAT('%', :query, '%')"
          )
    List<Product> searchProduct(String query);

}
