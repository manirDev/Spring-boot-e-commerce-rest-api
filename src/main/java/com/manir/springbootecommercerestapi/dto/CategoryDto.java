package com.manir.springbootecommercerestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.manir.springbootecommercerestapi.model.Category;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryDto {

    private Long id;
    private String title;
    private String keywords;
    private String description;
    private String status;

    //private Category parent;
    private Set<Category> children;

    //private Set<ProductDto> products;
}
