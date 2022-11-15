package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.dto.OrderDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.model.Order;
import com.manir.springbootecommercerestapi.model.OrderProducts;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.CartItemRepository;
import com.manir.springbootecommercerestapi.repository.OrderRepository;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.service.OrderProductsService;
import com.manir.springbootecommercerestapi.service.OrderService;
import com.manir.springbootecommercerestapi.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderProductsService orderProductsService;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final ShoppingCartService shoppingCartService;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void placeOrder(User customer) {
        CartItemResponse cartItemDto = shoppingCartService.findByCustomer(customer);
        OrderDto orderDto = new OrderDto();
        orderDto.setTotalPrice(cartItemDto.getTotalCost());
        orderDto.setEmail(customer.getEmail());
        orderDto.setName(customer.getName());
        OrderDto savedOrder = saveOrder(orderDto, customer);
        List<CartItemDto> cartItemDtoList = cartItemDto.getContent();
        for(CartItemDto cartItem : cartItemDtoList){
            OrderProducts orderProducts = new OrderProducts();
            orderProducts.setCustomer(customer);
            orderProducts.setProduct(cartItem.getProduct());
            orderProducts.setOrder(mapToEntity(savedOrder));
            orderProducts.setProductPrice(cartItem.getProduct().getPrice());
            orderProducts.setProductQuantity(cartItem.getQuantity());
            orderProducts.setTotalPrice(cartItemDto.getTotalCost());
            orderProductsService.addOrderProducts(orderProducts);
        }
        cartItemRepository.deleteByCustomerId(customer.getId());
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto, User customer) {
        //convert to entity
        Order order = mapToEntity(orderDto);
        //save order to db
        Order placedOrder = orderRepository.save(order);
        return mapToDto(placedOrder);
    }

    @Override
    public List<OrderDto> listOrdersByCustomer(User customer) {
        List<Order> orders = orderRepository.findByCustomer(customer);
        if (orders.size() == 0){
            throw new EcommerceApiException("User has no order", HttpStatus.BAD_REQUEST);
        }
        List<OrderDto> orderDtoList = orders.stream()
                                                    .map(order -> mapToDto(order))
                                                    .collect(Collectors.toList());
        return orderDtoList;
    }

    //map to Entity
    private Order mapToEntity(OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        return order;
    }
    //map to Dto
    private OrderDto mapToDto(Order order){
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }
}
