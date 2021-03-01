package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.CompanyAlreadyExistsException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private String[] states = {"AK", "AL", "AR", "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MP", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY"};


    @GetMapping("/list")
    public String listAllCompanies(Model model){
        model.addAttribute("companies", companyService.findAll());

        return "/company/company-list";
    }

    @GetMapping("/add")
    public String addNewCompanyForm(Model model){

        model.addAttribute("company", new CompanyDto());
        model.addAttribute("states", states);

        return "/company/company-add";
    }

    @PostMapping("/add")
    public String addNewCompany(CompanyDto companyDto) throws CompanyAlreadyExistsException {
        companyService.create(companyDto);

        return "redirect:/company/list";
    }


    @GetMapping("/update/{companyEmail}")
    public String editCompany(@PathVariable String companyEmail, Model model) throws CompanyNotFoundException {

        model.addAttribute("states", states);
        model.addAttribute("company",companyService.findByEmail(companyEmail));

        return "/company/company-update";
    }

    @PostMapping("/update/{companyEmail}")
    public String updateCompany(@ModelAttribute("company") CompanyDto companyDto,
                                @RequestParam(value="action", required=true) String action) throws CompanyNotFoundException {

        if (action.equals("save")) companyService.update(companyDto);

        if (action.equals("delete")) companyService.delete(companyDto);

        if (action.equals("reset")) return "redirect:/company/list";

        return "redirect:/company/list";
    }

//    @PostMapping(value = "/update/{companyEmail}",params ="delete")
//    public String deleteCompany(@ModelAttribute("company") CompanyDto companyDto) throws CompanyNotFoundException {
//        companyService.delete(companyDto);
//
//        return "redirect:company/list";
//    }

}
