package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
