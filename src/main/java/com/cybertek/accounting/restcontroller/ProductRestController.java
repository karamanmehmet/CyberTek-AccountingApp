package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductRestController {

    private final ProductService service;

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        return  service.findAll();
    }
}
