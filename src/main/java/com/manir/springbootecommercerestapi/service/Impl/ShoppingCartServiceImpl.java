package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.model.CartItem;
import com.manir.springbootecommercerestapi.model.Customer;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.repository.CartItemRepository;
import com.manir.springbootecommercerestapi.repository.CustomerRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private CartItemRepository cartItemRepository;
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private ProductRepository productRepository;
    @Resource
    private CustomerRepository customerRepository;

    @Override
    public List<CartItemDto> findByCustomerId(Long customerId) {

        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);

        if (cartItems.size() == 0){
            throw new EcommerceApiException("Customer has no product in cart item", HttpStatus.BAD_REQUEST);
        }

        List<CartItemDto> cartItemDtoList = cartItems.stream()
                                                     .map(cartItem -> mapToDto(cartItem))
                                                     .collect(Collectors.toList());

        return cartItemDtoList;
    }

    @Override
    public CartItemDto addCartItem(Long customerId, Long productId, Integer quantity) {
        Integer addedQuantity = quantity;
        Customer customer = customerRepository.findById(customerId)
                                              .orElseThrow(
                                                    () -> new ResourceNotFoundException("Customer", customerId)
                                              );
        Product product = productRepository.findById(productId)
                                           .orElseThrow(
                                                    () -> new ResourceNotFoundException("Product", productId)
                                           );

        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }else {
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        CartItem addedCartItem = cartItemRepository.save(cartItem);

        return mapToDto(addedCartItem);
    }

    @Override
    public CartItemDto updateItemQuantity(Long customerId, Long productId, Integer quantity) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", customerId)
                );
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", productId)
                );

        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if (!cartItem.getCustomer().getId().equals(customer.getId()) || !cartItem.getProduct().getId().equals(product.getId())){
            throw new EcommerceApiException(" this cart item does not belong to Customer or Product ", HttpStatus.BAD_REQUEST);
        }
        cartItem.setQuantity(quantity);
        CartItem updatedItemQuantity = cartItemRepository.save(cartItem);
        return mapToDto(updatedItemQuantity);
    }

    @Override
    @Transactional
    public void deleteItemProduct(Long customerId, Long productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", customerId)
                );
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", productId)
                );
        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if (cartItem != null){
            cartItemRepository.deleteByCustomerIdAndProductId(customerId, productId);
        }
        if (!cartItem.getCustomer().getId().equals(customer.getId()) || !cartItem.getProduct().getId().equals(product.getId())){
            throw new EcommerceApiException(" this cart item does not belong to Customer or Product ", HttpStatus.BAD_REQUEST);
        }
    }

    //map to dto
    private CartItemDto mapToDto(CartItem cartItem){
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        return cartItemDto;
    }

    //map to entity
    private CartItem mapToEntity(CartItemDto cartItemDto){
        CartItem cartItem = modelMapper.map(cartItemDto, CartItem.class);
        return cartItem;
    }
}
