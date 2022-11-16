package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.OrderProductsDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.model.OrderProducts;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.OrderProductsRepository;
import com.manir.springbootecommercerestapi.service.OrderProductsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderProductsServiceImpl implements OrderProductsService {

    @Resource(name = "orderProductsRepository")
    private final OrderProductsRepository orderProductsRepository;
    @Resource(name = "modelMapper")
    private final ModelMapper modelMapper;

    @Override
    public void addOrderProducts(OrderProducts orderProducts) {
        orderProductsRepository.save(orderProducts);
    }

    @Override
    public List<OrderProductsDto> findOrderItemsByCustomer(User customer) {

        List<OrderProducts> orderProducts = orderProductsRepository.findByCustomer(customer);
        if (orderProducts.size() == 0){
            throw new EcommerceApiException("User has no ordered products", HttpStatus.BAD_REQUEST);
        }
        List<OrderProductsDto> orderProductsDtoList =  orderProducts.stream()
                                                                            .map(orderProduct -> mapToDto(orderProduct))
                                                                            .collect(Collectors.toList());
        return orderProductsDtoList;
    }

    //map to dto
    private OrderProductsDto mapToDto(OrderProducts orderProducts){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderProductsDto orderProductsDto = modelMapper.map(orderProducts, OrderProductsDto.class);

        return orderProductsDto;
    }
}
