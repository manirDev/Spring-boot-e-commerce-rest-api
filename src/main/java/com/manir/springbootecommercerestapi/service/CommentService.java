package com.manir.springbootecommercerestapi.service;


import com.manir.springbootecommercerestapi.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long productId, CommentDto commentDto);

    List<CommentDto> getAllComments();
    List<CommentDto> getAllCommentsByProductId(Long productId);
    CommentDto getCommentById(Long productId, Long commentId);
    CommentDto updateComment(Long productId, CommentDto commentDto, Long commentId);
    void deleteComment(Long productId, Long commentId);

}
