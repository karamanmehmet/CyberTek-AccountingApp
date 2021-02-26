package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.CategoryDto;
import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.entity.ClientVendor;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ExistentClientVendorException;
import com.cybertek.accounting.service.ClientVendorService;
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
@RequestMapping("/clientvendor")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;
    private final CompanyService companyService;

    @GetMapping("/all")
    public String showClientVendors(Model model) throws CompanyNotFoundException {

        model.addAttribute("clientVendors",clientVendorService.findAllByCompany(companyService.findByEmail("karaman@crustycloud.com")));

        return "/clientvendor/vendor-client-list";

    }
    // After click Add CV
    @GetMapping("/create")
    public String addClientVendorForm(Model model) {
        model.addAttribute("clientvendor", new ClientVendorDto());
        return "/clientvendor/vendor-client-add";
    }

    //After click Save Changes
    @PostMapping("/all")
    public String addClientVendor(@ModelAttribute("clientvendor") ClientVendorDto clientVendorDto) throws ExistentClientVendorException {

        clientVendorService.create(clientVendorDto);
        return "redirect:/clientvendor/all";

    }

}
