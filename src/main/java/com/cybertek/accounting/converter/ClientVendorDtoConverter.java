//package com.cybertek.accounting.converter;
//
//import com.cybertek.accounting.dto.ClientVendorDto;
//import com.cybertek.accounting.service.ClientVendorService;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationPropertiesBinding
//public class ClientVendorDtoConverter implements Converter<String, ClientVendorDto> {
//
//    private ClientVendorService service;
//
//    @Autowired
//    public void setService(ClientVendorService service){
//        this.service = service;
//    }
//
//    @SneakyThrows
//    @Override
//    public ClientVendorDto convert(String s) {
//        return service.findByEmail(s);
//    }
//}
