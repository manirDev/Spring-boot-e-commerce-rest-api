package com.manir.springbootecommercerestapi.service;

public interface MapperService<E, D>{
    //entity mapper
    E mapToEntity(D type);

    //dto mapper
    D mapToDto(E type);
}
