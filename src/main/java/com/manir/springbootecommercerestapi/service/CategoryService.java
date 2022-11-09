package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    List<CategoryDto> getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir);
    CategoryDto getCategoryById(Long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
    void deleteCategory(Long categoryId);

    //List<CategoryDto> findChildren(Long parentId);

}
