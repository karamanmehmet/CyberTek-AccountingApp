package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.CategoryAlreadyExistException;
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

    @GetMapping("/list")
    public String showCategories(Model model) throws CompanyNotFoundException {
        // TODO This part will update according to valid user
        model.addAttribute("categories",categoryService.findAllByCompany(companyService.findByEmail("karaman@crustycloud.com")));
        return "/category/category-list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "/category/category-add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto) throws CategoryAlreadyExistException, CompanyNotFoundException {
        categoryService.create(categoryDto);

        return "redirect:/category/list";
    }
    // JUST FOR TEST PURPOSE
    @GetMapping("/test")
    public String addCategory() {

        return "/form-validation";
    }


}
