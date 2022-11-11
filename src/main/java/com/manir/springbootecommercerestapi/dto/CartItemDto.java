package com.manir.springbootecommercerestapi.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private String status;
}
