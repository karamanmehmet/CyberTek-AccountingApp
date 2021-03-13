package com.cybertek.accounting.configuration;

import com.cybertek.accounting.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig{
    
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    @Qualifier("restService-WebClient")
    public WebClient webClientRest(@Value("${rest.api.url}") String url) {
        return WebClient
                .builder()
                .baseUrl(url)
                .build();

    }

}
