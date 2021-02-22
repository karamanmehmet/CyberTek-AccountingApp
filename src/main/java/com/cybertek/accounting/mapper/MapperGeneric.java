package com.cybertek.accounting.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class MapperGeneric {

    private final ModelMapper modelMapper;

    public <T> T convert(Object toBeConverted, T converted) {
        return modelMapper.map(toBeConverted, (Type) converted.getClass());
    }

}
