package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.CommentDto;
import com.manir.springbootecommercerestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
public class CommentController {

    @Resource
    private CommentService commentService;

    //create comment api
    @PostMapping("/{productId}/createComment")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long productId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto responseComment = commentService.createComment(productId, commentDto);
        return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    //get all comments api
    @GetMapping("/getAllComments")
    public List<CommentDto> getAllComments(){
        return commentService.getAllComments();
    }

    //get comments by product id api
    @GetMapping("/{productId}/getCommentByProductId")
    public List<CommentDto> getCommentByProductId(@PathVariable Long productId){
        return commentService.getAllCommentsByProductId(productId);
    }

    //get comment by id api
    @GetMapping("/{productId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long productId, @PathVariable Long commentId){
        CommentDto responseComment = commentService.getCommentById(productId, commentId);
        return ResponseEntity.ok(responseComment);
    }

    //update comment api
    @PutMapping("/{productId}/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long productId,
                                                    @RequestBody CommentDto commentDto,
                                                    @PathVariable Long commentId){
        CommentDto responseComment = commentService.updateComment(productId, commentDto, commentId);
        return ResponseEntity.ok(responseComment);
    }

    //delete comment api
    @DeleteMapping("/{productId}/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long productId, @PathVariable Long commentId){
        commentService.deleteComment(productId, commentId);
        return ResponseEntity.ok("Comment with id: "+commentId+" is successfully:)");
    }
}
