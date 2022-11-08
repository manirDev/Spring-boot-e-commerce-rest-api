package com.manir.springbootecommercerestapi.dto;
import com.manir.springbootecommercerestapi.resource.ImageData;
import lombok.Data;

import java.util.List;
import java.util.Set;

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

    private String image;

    private CategoryDto category;

    private Set<ImageDataDto> images;
}
