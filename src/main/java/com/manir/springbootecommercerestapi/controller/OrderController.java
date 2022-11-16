package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.OrderDto;
import com.manir.springbootecommercerestapi.dto.OrderProductsDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import com.manir.springbootecommercerestapi.service.OrderProductsService;
import com.manir.springbootecommercerestapi.service.OrderService;
import com.manir.springbootecommercerestapi.utils.isAuthenticatedAsAdminOrUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/order")
public class OrderController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderProductsService orderProductsService;

    //place order complete order api
    @isAuthenticatedAsAdminOrUser
    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserEmail = authentication.getName();
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("Customer Not found"));
            orderService.placeOrder(customer);
            return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
        }else{
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }

    //find order by customer api
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findByCustomer")
    public List<OrderDto> listOrdersByCustomer(@AuthenticationPrincipal Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserEmail = authentication.getName();
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("Customer Not found"));
            List<OrderDto> customerOrders =  orderService.listOrdersByCustomer(customer);
            return customerOrders;
        }else{
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }

    //find ordered items by Customer
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findOrderedItemsByCustomer")
    public List<OrderProductsDto> findOrderedItemsByCustomer(@AuthenticationPrincipal Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUserEmail = authentication.getName();
            User customer = userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("Customer Not found"));
            List<OrderProductsDto> customerOrderedItems =  orderProductsService.findOrderItemsByCustomer(customer);
            return customerOrderedItems;
        }else{
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
    }
}
