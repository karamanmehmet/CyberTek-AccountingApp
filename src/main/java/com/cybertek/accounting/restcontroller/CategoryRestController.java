package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService service;

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories(){
        return  service.findAll();
    }
}
