package com.manir.springbootecommercerestapi.dto;

import lombok.Data;

@Data
public class FileDataDto {
    private Long id;
    private String name;
    private String type;
    private String filePath;
}
