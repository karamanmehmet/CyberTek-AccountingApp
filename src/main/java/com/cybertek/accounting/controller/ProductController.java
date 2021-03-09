package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ProductAlreadyExistException;
import com.cybertek.accounting.exception.ProductFieldNullException;
import com.cybertek.accounting.exception.ProductNotFoundException;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CompanyService companyService;
    private final CategoryService categoryService;

    @GetMapping("/list")
    public String getAllProducts(Model model) {

        //TODO: Change the static values below.

        List<ProductDto> products = new ArrayList<>();

        try {
            products = productService.findByCompany(companyService.findByEmail("karaman@crustycloud.com"));
        } catch (CompanyNotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("products", products);

        return "/product/product-list";
    }

    @GetMapping("/add")
    public String createProduct(Model model) {

        //TODO: Change the static values below.

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String loggedInUser = authentication.getName();
//        User user = userService.findByEmail(loggedInUser);

        List<CategoryDto> categories = new ArrayList<>();

        try {
            categories = categoryService.findAllByCompany(companyService.findByEmail("karaman@crustycloud.com"));
        } catch (CompanyNotFoundException e) {
            e.printStackTrace();
        }

        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categories);

        return "/product/product-add";
    }

    @PostMapping
    public String insertProduct(@ModelAttribute ProductDto productDto) {

        try {
            productService.create(productDto);
        } catch (ProductFieldNullException | ProductAlreadyExistException | CompanyNotFoundException e) {
            e.printStackTrace();
        }

        return "redirect:/product/list";
    }

    @GetMapping("/update")
    public String editProduct(@ModelAttribute ProductDto productDto, Model model) {
        model.addAttribute("product", productDto);
        return "redirect:/product/add";
    }

    @PutMapping
    public String updateProduct(ProductDto productDto) {
        try {
            productService.update(productDto);
        } catch (ProductFieldNullException | ProductNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/product/list";
    }

}
