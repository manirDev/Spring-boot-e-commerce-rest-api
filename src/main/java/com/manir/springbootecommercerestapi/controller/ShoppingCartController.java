package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    //find by customer api
    @GetMapping("/findByCustomer/{customerId}")
    public List<CartItemDto> findByCustomerId(@PathVariable Long customerId){
        List<CartItemDto> responseCartItems = shoppingCartService.findByCustomerId(customerId);

        return responseCartItems;
    }

    //add item to the cart api
    @PostMapping("/addItem/{customerId}/{productId}/{quantity}")
    public ResponseEntity<CartItemDto> addCartItem(@PathVariable Long customerId,
                                                   @PathVariable Long productId,
                                                   @PathVariable Integer quantity){
        CartItemDto responseCartItem = shoppingCartService.addCartItem(customerId, productId, quantity);

        return new ResponseEntity<>(responseCartItem, HttpStatus.CREATED);
    }

    //update item quantity api
    @PutMapping("/updateItemQuantity/{customerId}/{productId}/{quantity}")
    public ResponseEntity<CartItemDto> updateItemQuantity(@PathVariable Long customerId,
                                                          @PathVariable Long productId,
                                                          @PathVariable Integer quantity){

        CartItemDto responseCartItem = shoppingCartService.updateItemQuantity(customerId, productId, quantity);

        return  new ResponseEntity<>(responseCartItem, HttpStatus.OK);
    }

    //delete item product api
    @DeleteMapping("/deleteItemProduct/{customerId}/{productId}")
    public ResponseEntity<String> deleteItemProduct(@PathVariable Long customerId, @PathVariable Long productId){
        shoppingCartService.deleteItemProduct(customerId, productId);
        return ResponseEntity.ok("Product deleted successfully from cart item");
    }
}
