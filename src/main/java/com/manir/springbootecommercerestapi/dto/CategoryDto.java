package com.manir.springbootecommercerestapi.dto;

import com.manir.springbootecommercerestapi.resource.Product;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    private Long id;
    private String title;
    private String keywords;
    private String description;
    private String status;

    //private Set<ProductDto> products;
}
