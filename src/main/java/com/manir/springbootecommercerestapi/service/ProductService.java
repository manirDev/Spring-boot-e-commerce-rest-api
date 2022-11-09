package com.manir.springbootecommercerestapi.service;

import com.manir.springbootecommercerestapi.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, MultipartFile file);
    List<ProductDto> getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(Long categoryId, ProductDto productDto, Long productId);
    void deleteProduct(Long productId);

    ProductDto saveProductByCategoryId(Long categoryId, ProductDto productDto);
}
