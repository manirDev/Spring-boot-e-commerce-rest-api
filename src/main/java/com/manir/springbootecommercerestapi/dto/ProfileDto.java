package com.manir.springbootecommercerestapi.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private Long id;
    private String image;
    private String address;
    private String phone;
}
