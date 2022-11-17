package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
