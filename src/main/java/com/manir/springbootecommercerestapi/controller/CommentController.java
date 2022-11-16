package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.CommentDto;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.service.CommentService;
import com.manir.springbootecommercerestapi.service.CommonService;
import com.manir.springbootecommercerestapi.utils.isAuthenticatedAsAdminOrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommonService commonService;

    //create comment api
    @isAuthenticatedAsAdminOrUser
    @PostMapping("/{productId}/createComment")
    public ResponseEntity<CommentDto> createComment(@AuthenticationPrincipal Authentication authentication,
                                                    @PathVariable Long productId,
                                                    @RequestBody CommentDto commentDto){
        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        CommentDto responseComment = commentService.createComment(customer, productId, commentDto);
        return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    //get comment by user
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/comment/findByUser")
    public List<CommentDto> findByUser(@AuthenticationPrincipal Authentication authentication){
        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        return commentService.findCommentByCustomer(customer);
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
