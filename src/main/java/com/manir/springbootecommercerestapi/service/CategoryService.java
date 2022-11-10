package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.CategoryDto;
import com.manir.springbootecommercerestapi.response.CategoryResponse;
import com.manir.springbootecommercerestapi.response.CommonResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CommonResponse getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir);
    CategoryDto getCategoryById(Long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
    void deleteCategory(Long categoryId);

    //List<CategoryDto> findChildren(Long parentId);

}
