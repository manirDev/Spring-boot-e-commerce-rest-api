package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
