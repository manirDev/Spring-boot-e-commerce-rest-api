package com.manir.springbootecommercerestapi.service;


import com.manir.springbootecommercerestapi.dto.CommentDto;
import com.manir.springbootecommercerestapi.model.User;

import java.util.List;

public interface CommentService {

    CommentDto createComment(User customer, Long productId, CommentDto commentDto);

    List<CommentDto> findCommentByCustomer(User customer);
    List<CommentDto> getAllComments();
    List<CommentDto> getAllCommentsByProductId(Long productId);
    CommentDto getCommentById(Long productId, Long commentId);
    CommentDto updateComment(Long productId, CommentDto commentDto, Long commentId);
    void deleteComment(Long productId, Long commentId);

}
