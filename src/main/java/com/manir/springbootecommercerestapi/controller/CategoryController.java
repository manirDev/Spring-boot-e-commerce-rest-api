package com.manir.springbootecommercerestapi.controller;

import com.manir.springbootecommercerestapi.dto.CategoryDto;
import com.manir.springbootecommercerestapi.response.CommonResponse;
import com.manir.springbootecommercerestapi.service.CategoryService;
import com.manir.springbootecommercerestapi.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //create category api
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto responseCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(responseCategory, HttpStatus.CREATED);
    }

    //get all categories api
    @GetMapping("/getAllCategory")
    public CommonResponse getAllCategory(@RequestParam(value = "pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                         @RequestParam(value = "sortBy", defaultValue = Constant.DEFAULT_SORT_BY, required = false) String sortBy,
                                         @RequestParam(value = "sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        CommonResponse categoryResponse = categoryService.getAllCategory(pageNo, pageSize, sortBy, sortDir);
        return categoryResponse;
    }

    //get category by id api
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDto> getCatecoryById(@PathVariable Long categoryId){
        CategoryDto responseCategory = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(responseCategory);
    }

    //update category api
    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable Long categoryId){
        CategoryDto responseCategory = categoryService.updateCategory(categoryDto, categoryId);
        return  new ResponseEntity<>(responseCategory, HttpStatus.OK);
    }

    //delete category api
    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category with id: "+ categoryId +  " is deleted successfully:)");
    }
}
