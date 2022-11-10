package com.manir.springbootecommercerestapi.service.Impl;

import com.manir.springbootecommercerestapi.dto.CategoryDto;
import com.manir.springbootecommercerestapi.exception.ResourceNotFoundException;
import com.manir.springbootecommercerestapi.repository.CategoryRepository;
import com.manir.springbootecommercerestapi.model.Category;
import com.manir.springbootecommercerestapi.response.CategoryResponse;
import com.manir.springbootecommercerestapi.service.CategoryService;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
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
    public CategoryResponse getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> categories = categoryRepository.findAll(pageable);

        List<Category> categoryList = categories.getContent();
        //map all categories to dto
        List<CategoryDto> categoryDtoList = categoryList.stream()
                                                      .map(category -> mapToDto(category))
                                                      .collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtoList);
        categoryResponse.setPageNo(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setLast(categories.isLast());

        return categoryResponse;
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

//    private static void listCategories(Session session) {
//        Category electronics = session.get(Category.class, 1);
//
//        Set<Category> children = electronics.getChildren();
//
//        System.out.println(electronics.getTitle());
//
//        for (Category child : children) {
//            System.out.println("--" + child.getTitle());
//            printChildren(child, 1);
//        }
//    }
//
//    private static void printChildren(Category parent, int subLevel) {
//        Set<Category> children = parent.getChildren();
//
//        for (Category child : children) {
//            for (int i = 0; i <= subLevel; i++) System.out.print("--");
//
//            System.out.println(child.getTitle());
//
//            printChildren(child, subLevel + 1);
//        }
//    }
}
