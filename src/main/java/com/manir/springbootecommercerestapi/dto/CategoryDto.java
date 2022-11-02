package com.manir.springbootecommercerestapi.dto;

import lombok.Data;

@Data
public class CategoryDto {

    private Long id;
    private String title;
    private String keywords;
    private String description;
    private String status;
}
