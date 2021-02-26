package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ExistentCategoryException;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CompanyService companyService;

    // After click Category
    @GetMapping("/all")
    public String showCategories(Model model) throws CompanyNotFoundException {
        // TODO This part will update according to valid user
        model.addAttribute("categories",categoryService.findAllByCompany(companyService.findByEmail("karaman@crustycloud.com")));
        return "/category/category-list";
    }

    // After click Add Category
    @GetMapping("/create")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "/category/category-add";
    }

   //After click Save Changes
    @PostMapping("/all")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto) throws ExistentCategoryException, CompanyNotFoundException {
        categoryService.create(categoryDto);

        return "redirect:/category/all";
        // it will continue from getMapping  categories
    }


}