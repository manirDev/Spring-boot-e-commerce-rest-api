package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.service.CommonService;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import com.manir.springbootecommercerestapi.utils.isAuthenticatedAsAdminOrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/v1/cart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CommonService commonService;

    //find by customer api
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findByCustomer")
    public CartItemResponse findByCustomerId(@AuthenticationPrincipal Authentication authentication){

        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        CartItemResponse responseCartItems = shoppingCartService.findByCustomer(customer);
        return responseCartItems;
    }

    //add item to the cart api
    @isAuthenticatedAsAdminOrUser
    @PostMapping("/addItem/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> addCartItem(@AuthenticationPrincipal Authentication authentication,
                                                        @PathVariable Long productId,
                                                        @PathVariable Integer quantity){

        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        CartItemResponse responseCartItem = shoppingCartService.addCartItem(customer, productId, quantity);
        return new ResponseEntity<>(responseCartItem, HttpStatus.CREATED);
    }

    //update item quantity api
    @isAuthenticatedAsAdminOrUser
    @PutMapping("/updateItemQuantity/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> updateItemQuantity(@AuthenticationPrincipal Authentication authentication,
                                                               @PathVariable Long productId,
                                                               @PathVariable Integer quantity){
        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        CartItemResponse responseCartItem = shoppingCartService.updateItemQuantity(customer, productId, quantity);
        return  new ResponseEntity<>(responseCartItem, HttpStatus.OK);
    }

    //delete item product api
    @isAuthenticatedAsAdminOrUser
    @DeleteMapping("/deleteItemProduct/{productId}")
    public ResponseEntity<String> deleteItemProduct(@AuthenticationPrincipal Authentication authentication,
                                                    @PathVariable Long productId){

        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        shoppingCartService.deleteItemProduct(customer, productId);
        return ResponseEntity.ok("Product with id = " + productId +" is deleted successfully from your shopping cart");
    }




}
