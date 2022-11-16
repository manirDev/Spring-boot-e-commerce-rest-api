package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.repository.UserRepository;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.response.CommonResponse;
import com.manir.springbootecommercerestapi.service.CommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CommonServiceImpl implements CommonService{

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Resource(name = "userRepository")
    private final UserRepository userRepository;

    @Override
    public CommonResponse getResponseContent(Page page, List dtoList) {

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setContent(dtoList);
        commonResponse.setPageNo(page.getNumber());
        commonResponse.setPageSize(page.getSize());
        commonResponse.setTotalPages(page.getTotalPages());
        commonResponse.setTotalElements(page.getTotalElements());
        commonResponse.setLast(page.isLast());

        return commonResponse;
    }

    @Override
    public CartItemResponse getResponse(CartItemDto cartItemDto) {

        CartItemResponse cartItemResponse = new CartItemResponse();

        double totalPrice = cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity();
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        cartItemDtoList.add(cartItemDto);
        cartItemResponse.setContent(cartItemDtoList);
        cartItemResponse.setTotalCost(totalPrice);
        return cartItemResponse;
    }

    @Override
    public User getCurrentAuthenticatedUser(Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken){
            throw new EcommerceApiException("User not authenticated", HttpStatus.BAD_REQUEST);
        }
        String currentUserEmail = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                                         .orElseThrow(
                                                () -> new UsernameNotFoundException("User Not found")
                                         );

        return currentUser;
    }
}
