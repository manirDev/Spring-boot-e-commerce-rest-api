package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.model.CartItem;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.model.Product;
import com.manir.springbootecommercerestapi.repository.CartItemRepository;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import com.manir.springbootecommercerestapi.repository.ProductRepository;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.service.CommonService;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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
    private UserRepository userRepository;
    @Resource
    private CommonService commonService;
    @Override
    public CartItemResponse findByCustomerId(Long customerId) {

        List<CartItem> cartItems = cartItemRepository.findByCustomerId(customerId);

        if (cartItems.size() == 0){
            throw new EcommerceApiException("User has no product in cart item", HttpStatus.BAD_REQUEST);
        }

        List<CartItemDto> cartItemDtoList = cartItems.stream()
                                                     .map(cartItem -> mapToDto(cartItem))
                                                     .collect(Collectors.toList());
        DoubleStream totalPrice = cartItemDtoList.stream()
                                                 .mapToDouble(cartItemDto -> cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setContent(cartItemDtoList);
        cartItemResponse.setTotalCost(totalPrice.sum());
        return cartItemResponse;
    }

    @Override
    public CartItemResponse addCartItem(Long customerId, Long productId, Integer quantity) {
        Integer addedQuantity = quantity;
        User user = findCustomerById(customerId);
        Product product = findProductById(productId);

        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if(cartItem != null){
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        }else {
            cartItem = new CartItem();
            cartItem.setCustomer(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        CartItem addedCartItem = cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = mapToDto(addedCartItem);

        CartItemResponse cartItemResponse = commonService.getResponse(cartItemDto);
        return cartItemResponse;
    }

    @Override
    public CartItemResponse updateItemQuantity(Long customerId, Long productId, Integer quantity) {

        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if (cartItem == null){
            throw new EcommerceApiException("Product is not in the cart item", HttpStatus.BAD_REQUEST);
        }
        cartItem.setQuantity(quantity);
        CartItem updatedItemQuantity = cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = mapToDto(updatedItemQuantity);

        CartItemResponse cartItemResponse = commonService.getResponse(cartItemDto);
        return cartItemResponse;
    }

    @Override
    @Transactional
    public void deleteItemProduct(Long customerId, Long productId) {

        CartItem cartItem = cartItemRepository.findByCustomerIdAndProductId(customerId, productId);
        if (cartItem == null){
            throw new EcommerceApiException("Product is not in the cart item", HttpStatus.BAD_REQUEST);
        }
        cartItemRepository.deleteByCustomerIdAndProductId(customerId, productId);
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

    private User findCustomerById(Long customerId){
        User user = userRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User", customerId)
                );
        return user;
    }

    private Product findProductById(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", productId)
                );
        return product;
    }


}
