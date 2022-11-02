package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CategoryDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.CategoryRepository;
import com.manir.springbootecommercerestapi.resource.Category;
import com.manir.springbootecommercerestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;
    @Resource(name = "modelMapper")
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        //map dto to entity
        Category category = mapToEntity(categoryDto);
        //save entity
        Category newCategory = categoryRepository.save(category);
        //map back entity to dto
        CategoryDto responseCategoryDto = mapToDto(newCategory);

        return responseCategoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories = categoryRepository.findAll();
        //map all categories to dto
        List<CategoryDto> categoryDtoList = categories.stream()
                                                      .map(category -> mapToDto(category))
                                                      .collect(Collectors.toList());

        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(
                                                      () -> new ResourceNotFoundException("Category", categoryId)
                                              );
        //map entity to dto
        CategoryDto responseCategory = mapToDto(category);

        return responseCategory;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(
                                                    () -> new ResourceNotFoundException("Category", categoryId)
                                              );

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setKeywords(categoryDto.getKeywords());
        category.setStatus(categoryDto.getStatus());
        Category updatedCategory = categoryRepository.save(category);
        //map entity to dto
        CategoryDto responseCategory = mapToDto(updatedCategory);
        return responseCategory;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                                              .orElseThrow(
                                                    () -> new ResourceNotFoundException("Category", categoryId)
                                              );
        categoryRepository.delete(category);
    }

    private CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    private Category mapToEntity(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        return  category;
    }
}
