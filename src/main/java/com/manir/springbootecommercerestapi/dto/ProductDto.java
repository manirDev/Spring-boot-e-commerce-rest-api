package com.manir.springbootecommercerestapi.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
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

    private Set<CommentDto> comments;
}
