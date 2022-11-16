package com.manir.springbootecommercerestapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manir.springbootecommercerestapi.model.User;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalPrice;
    private String note;
    private  String status;

    @JsonIgnore
    private User customer;
}
