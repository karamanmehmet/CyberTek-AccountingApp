package com.cybertek.accounting.converter;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.service.CategoryService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CategoryDtoConverter implements Converter<String, CategoryDto> {

    private final CategoryService service;

    public CategoryDtoConverter(@Lazy CategoryService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public CategoryDto convert(String id) {
        return service.findById(Long.valueOf(id));
    }
}
