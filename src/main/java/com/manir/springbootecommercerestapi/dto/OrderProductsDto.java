package com.manir.springbootecommercerestapi.dto;

import lombok.Data;

@Data
public class OrderProductsDto {
    private Long id;
    private double productPrice;
    private Integer productQuantity;
    private double totalPrice;
    private String note;
    private String status;
}
