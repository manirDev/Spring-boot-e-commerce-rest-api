package com.manir.springbootecommercerestapi.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@AllArgsConstructor
@Service
@Component("mapperService")
public class MapperServiceImpl<E, D> implements MapperService<E, D> {


    @Resource(name = "modelMapper")
    private final ModelMapper modelMapper;
    @Resource
    private final Class<E> entityClass;
    @Resource
    private final Class<D> dtoClass;


    @Override
    public E mapToEntity(D type) {
        E model = modelMapper.map(type, entityClass);
        return model;
    }

    @Override
    public D mapToDto(E type) {
        D dto = modelMapper.map(type, dtoClass);
        return dto;
    }
}
