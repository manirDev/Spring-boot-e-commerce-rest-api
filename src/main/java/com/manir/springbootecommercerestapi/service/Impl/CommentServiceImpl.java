package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CommentDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.CommentRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.model.Comment;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private ModelMapper modelMapper;
    @Resource
    private CommentRepository commentRepository;
    @Resource
    private ProductRepository productRepository;

    @Override
    public CommentDto createComment(Long productId, CommentDto commentDto) {

        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", productId));

        //convert to entity
        Comment comment = mapToEntity(commentDto);
        //save to db
        comment.setProduct(product);
        Comment createdComment = commentRepository.save(comment);
        //convert to dto
        CommentDto responseComment = mapToDto(createdComment);

        return responseComment;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtoList = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public List<CommentDto> getAllCommentsByProductId(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        List<CommentDto> commentDtoList = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public CommentDto getCommentById(Long productId, Long commentId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", productId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", commentId));
        CommentDto responseComment = null;
        if (comment.getProduct().getId() == product.getId()){
            responseComment = mapToDto(comment);
        }

        return responseComment;
    }

    @Override
    public CommentDto updateComment(Long productId, CommentDto commentDto, Long commentId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", productId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", commentId));



        if (comment.getProduct().getId() == product.getId()){
            comment.setReview(commentDto.getReview());
            comment.setRate(commentDto.getRate());
            comment.setStatus(commentDto.getStatus());

        }

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long productId, Long commentId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", productId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", commentId));
        if (comment.getProduct().getId() == product.getId()){
            commentRepository.delete(comment);
        }
    }

    //map to dto
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    //map to entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment  = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}
