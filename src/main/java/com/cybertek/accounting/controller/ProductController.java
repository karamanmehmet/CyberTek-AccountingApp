package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ProductAlreadyExistException;
import com.cybertek.accounting.exception.ProductFieldNullException;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CompanyService companyService;
    private final CategoryService categoryService;

    @GetMapping
    public String getAllProducts(Model model) throws CompanyNotFoundException {

        List<ProductDto> products = productService.findByCompany(companyService.findByEmail("karaman@crustycloud.com"));
        model.addAttribute("products", products);

        return "/product/product-list";

    }

    @GetMapping("/add")
    public String createProduct(Model model) throws CompanyNotFoundException {

        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAllByCompany(companyService.findByEmail("karaman@crustycloud.com")));

        return "/product/product-add";
    }

    @PostMapping
    public String insertProduct(@ModelAttribute ProductDto productDto) throws CompanyNotFoundException, ProductFieldNullException, ProductAlreadyExistException {

        productService.create(productDto);

        return "redirect:/products";

        //TODO: Create converter for Category.

    }

}
