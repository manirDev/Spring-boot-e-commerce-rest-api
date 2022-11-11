package com.manir.springbootecommercerestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manir.springbootecommercerestapi.model.Product;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private String status;
    @JsonIgnore
    private Product product;
}
