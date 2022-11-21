package com.manir.springbootecommercerestapi.service;


import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Component("mapperService")
@Data
public class MapperServiceImpl<E, D> implements MapperService<E, D> {


    @Resource(name = "modelMapper")
    private final ModelMapper modelMapper;

    private Class<E> entityClass;

    private Class<D> dtoClass;

    public MapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public E mapToEntity(D type) {
        E model = modelMapper.map(type, getEntityClass());
        return model;
    }

    @Override
    public D mapToDto(E type) {
        D dto = modelMapper.map(type, getDtoClass());
        return dto;
    }
}
