package com.cybertek.accounting.converter;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.service.CompanyService;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService service;

    public CompanyDtoConverter(@Lazy CompanyService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public CompanyDto convert(String id) {
        return service.findById(Long.valueOf(id));
    }
}