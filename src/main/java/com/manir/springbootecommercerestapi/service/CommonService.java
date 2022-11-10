package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.response.CommonResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommonService<T> {

    //CommonResponse getAllCategoryOrProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    CommonResponse getResponseContent(Page<T> page, List<T> dtoList);
}
