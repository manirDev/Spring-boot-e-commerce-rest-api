package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import com.manir.springbootecommercerestapi.utils.isAuthenticatedAsAdminOrUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    //find by customer api
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findByCustomer/{customerId}")
    public CartItemResponse findByCustomerId(@PathVariable Long customerId){
        CartItemResponse responseCartItems = shoppingCartService.findByCustomerId(customerId);

        return responseCartItems;
    }

    //add item to the cart api
    @isAuthenticatedAsAdminOrUser
    @PostMapping("/addItem/{customerId}/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> addCartItem(@PathVariable Long customerId,
                                                        @PathVariable Long productId,
                                                        @PathVariable Integer quantity){
        CartItemResponse responseCartItem = shoppingCartService.addCartItem(customerId, productId, quantity);

        return new ResponseEntity<>(responseCartItem, HttpStatus.CREATED);
    }

    //update item quantity api
    @isAuthenticatedAsAdminOrUser
    @PutMapping("/updateItemQuantity/{customerId}/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> updateItemQuantity(@PathVariable Long customerId,
                                                               @PathVariable Long productId,
                                                               @PathVariable Integer quantity){

        CartItemResponse responseCartItem = shoppingCartService.updateItemQuantity(customerId, productId, quantity);

        return  new ResponseEntity<>(responseCartItem, HttpStatus.OK);
    }

    //delete item product api
    @isAuthenticatedAsAdminOrUser
    @DeleteMapping("/deleteItemProduct/{customerId}/{productId}")
    public ResponseEntity<String> deleteItemProduct(@PathVariable Long customerId, @PathVariable Long productId){
        shoppingCartService.deleteItemProduct(customerId, productId);
        return ResponseEntity.ok("Product with id = " + productId +" is deleted successfully from your shopping cart");
    }
}
