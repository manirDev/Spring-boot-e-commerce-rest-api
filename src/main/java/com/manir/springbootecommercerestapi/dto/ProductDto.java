package com.manir.springbootecommercerestapi.dto;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private String keywords;
    private String description;
    private String detail;
    private double price;
    private Integer quantity;
    private String status;

    private CategoryDto category;
}
