package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.CategoryDto;
import com.manir.springbootecommercerestapi.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProduct();
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(ProductDto productDto, Long productId);
    void deleteProduct(Long productId);
}
