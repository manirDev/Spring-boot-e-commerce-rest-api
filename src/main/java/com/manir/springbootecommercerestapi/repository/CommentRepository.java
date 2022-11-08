package com.manir.springbootecommercerestapi.repository;

import com.manir.springbootecommercerestapi.resource.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductId(Long productId);
}
