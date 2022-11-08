package com.manir.springbootecommercerestapi.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String review;
    private Integer rate;
    private String status;

}
