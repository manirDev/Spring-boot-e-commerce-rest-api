package com.manir.springbootecommercerestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
   private String resourceName;
   private Long fieldValue;

    public ResourceNotFoundException(String resourceName, Long fieldValue) {
        super(String.format("%s with id : %s is not found !", resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Long getFieldValue() {
        return fieldValue;
    }
}
