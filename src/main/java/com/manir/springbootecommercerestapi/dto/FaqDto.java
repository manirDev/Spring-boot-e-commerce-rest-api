package com.manir.springbootecommercerestapi.dto;

import com.manir.springbootecommercerestapi.model.BaseEntity;
import lombok.Data;

@Data
public class FaqDto extends BaseEntity {
    private Long id;
    private String question;
    private String answer;
    private String status;
}
