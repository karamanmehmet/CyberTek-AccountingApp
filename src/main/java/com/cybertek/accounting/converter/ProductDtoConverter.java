package com.cybertek.accounting.converter;

import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.service.ProductService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ProductDtoConverter implements Converter<String, ProductDto> {

    private final ProductService service;

    public ProductDtoConverter(@Lazy ProductService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public ProductDto convert(String id) {
        return service.findById(Long.valueOf(id));
    }
}
