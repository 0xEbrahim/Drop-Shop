package com.ibrahim.drop_shop.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class ResponseTransformer {

    private final ModelMapper modelMapper;

    public ResponseTransformer(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
     public <S, T> T transformToDto(S data, Class<T> dto) {
         return modelMapper.map( data, (Type) dto);
    }

    public <S, T> T transformFromDto(S dto, Class<T> data) {
        return modelMapper.map(dto, data);
    }
}
