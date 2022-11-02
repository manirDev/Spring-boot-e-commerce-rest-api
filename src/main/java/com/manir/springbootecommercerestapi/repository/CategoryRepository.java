package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.resource.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
