package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import com.manir.springbootecommercerestapi.utils.isAuthenticatedAsAdminOrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/v1/cart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserRepository userRepository;


    //find by customer api
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findByCustomer")
    public CartItemResponse findByCustomerId(@AuthenticationPrincipal Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            //System.out.println("Name:" + currentUserEmail);
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(()-> new UsernameNotFoundException("Customer not found"));
            CartItemResponse responseCartItems = shoppingCartService.findByCustomer(customer);
            return responseCartItems;

        }else{
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }

    }

    //add item to the cart api
    @isAuthenticatedAsAdminOrUser
    @PostMapping("/addItem/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> addCartItem(@AuthenticationPrincipal Authentication authentication,
                                                        @PathVariable Long productId,
                                                        @PathVariable Integer quantity){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserEmail = authentication.getName();
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
            CartItemResponse responseCartItem = shoppingCartService.addCartItem(customer, productId, quantity);
            return new ResponseEntity<>(responseCartItem, HttpStatus.CREATED);
        }else {
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }

    //update item quantity api
    @isAuthenticatedAsAdminOrUser
    @PutMapping("/updateItemQuantity/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> updateItemQuantity(@AuthenticationPrincipal Authentication authentication,
                                                               @PathVariable Long productId,
                                                               @PathVariable Integer quantity){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserEmail = authentication.getName();
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("Customer Not found"));
            CartItemResponse responseCartItem = shoppingCartService.updateItemQuantity(customer, productId, quantity);
            return  new ResponseEntity<>(responseCartItem, HttpStatus.OK);
        }else{
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }

    //delete item product api
    @isAuthenticatedAsAdminOrUser
    @DeleteMapping("/deleteItemProduct/{productId}")
    public ResponseEntity<String> deleteItemProduct(@AuthenticationPrincipal Authentication authentication,
                                                    @PathVariable Long productId){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserEmail = authentication.getName();
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("Customer Not found"));
            shoppingCartService.deleteItemProduct(customer, productId);
            return ResponseEntity.ok("Product with id = " + productId +" is deleted successfully from your shopping cart");
        }else{
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }
}
