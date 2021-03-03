package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.exception.*;
import com.cybertek.accounting.service.CategoryService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CompanyService companyService;

    @GetMapping("/list")
    public String showCategories(Model model) throws CompanyNotFoundException {

        model.addAttribute("categories",categoryService.findAll());
        return "/category/category-list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        return "/category/category-add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") CategoryDto categoryDto,@RequestParam(value="action", required=true) String action) throws CategoryAlreadyExistException, CompanyNotFoundException {
        if (action.equals("save")) {
            categoryService.create(categoryDto);
        }

        return "redirect:/category/list";
    }


    @GetMapping("/update/{id}")
    public String updateCategory(@PathVariable long id,Model model) throws CategoryNotFoundException {
        model.addAttribute("category",categoryService.findById(id));

        return "/category/category-update";

    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable long id, @ModelAttribute("category") CategoryDto categoryDto, @RequestParam(value="action", required=true) String action) throws CompanyNotFoundException, CategoryNotFoundException, CategoryHasProductException {

        if (action.equals("save")) {
            categoryService.update(categoryDto,id);
        }
        if (action.equals("delete")) {
            categoryService.delete(id);
        }

        return "redirect:/category/list";
    }

    @GetMapping("/test")
    public String addCategory() {

        return "/form-validation";
    }


}
