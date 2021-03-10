package com.cybertek.accounting.converter;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.service.ClientVendorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDtoConverter implements Converter<String, ClientVendorDto> {

    private final ClientVendorService service;

    public ClientVendorDtoConverter(@Lazy ClientVendorService service) {
        this.service = service;
    }

    @SneakyThrows
    @Override
    public ClientVendorDto convert(String id) {
        return service.findById(Long.valueOf(id));
    }
}
