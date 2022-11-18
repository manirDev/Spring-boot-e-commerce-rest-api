package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.CartItemDto;
import com.manir.springbootecommercerestapi.model.User;
import com.manir.springbootecommercerestapi.response.CartItemResponse;
import com.manir.springbootecommercerestapi.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommonService<T> {

    //CommonResponse getAllCategoryOrProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    CommonResponse getResponseContent(Page<T> page, List<T> dtoList);

    //cart iem response handler
    CartItemResponse getResponse(CartItemDto cartItemDto);

    //get current authenticated user
    User getCurrentAuthenticatedUser(Authentication authentication);

    //entity mapper
    T mapToEntity(T type);

    //dto mapper
    T mapToDto(T type);
}
