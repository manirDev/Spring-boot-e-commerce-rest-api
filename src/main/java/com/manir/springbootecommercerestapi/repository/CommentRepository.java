package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.model.Comment;
import com.manir.springbootecommercerestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductId(Long productId);
    List<Comment> findByCustomer(User customer);
}
