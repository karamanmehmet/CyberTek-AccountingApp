package com.cybertek.accounting.controller;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ClientVendorAlreadyExistException;
import com.cybertek.accounting.service.ClientVendorService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clientvendor")
public class ClientVendorController {

    private final ClientVendorService clientVendorService;

    @GetMapping("/list")
    public String showClientVendors(Model model) throws CompanyNotFoundException {
        model.addAttribute("clientVendors",clientVendorService.findAll());

        return "/clientvendor/vendor-client-list";

    }
    @GetMapping("/add")
    public String addClientVendorForm(Model model) {
        model.addAttribute("clientvendor", new ClientVendorDto());
        return "/clientvendor/vendor-client-add";
    }

    @GetMapping("/update/{id}")
    public String updateClientVendor(@PathVariable long id,Model model) throws ClientVendorNotFoundException {
        model.addAttribute("clientvendor",clientVendorService.findById(id));

        return "/clientvendor/vendor-client-update";

    }
    // NEED TO TALK ABOUT LOGIC
    @PostMapping("/update/{id}")
    public String updateClientVendor(@PathVariable long id,@ModelAttribute("clientvendor") ClientVendorDto clientVendorDto,@RequestParam(value="action", required=true) String action) throws ClientVendorNotFoundException, CompanyNotFoundException, ClientVendorAlreadyExistException {
        if (action.equals("save")) {
            clientVendorService.update1(clientVendorDto,id);
        }
        if (action.equals("delete")) {
            clientVendorService.delete(id);
        }

        return "redirect:/clientvendor/list";
    }

    @PostMapping("/add")
    public String addClientVendor(@ModelAttribute("clientvendor") ClientVendorDto clientVendorDto,@RequestParam(value="action", required=true) String action) throws ClientVendorAlreadyExistException, CompanyNotFoundException {

        if (action.equals("save")) {
            clientVendorService.create1(clientVendorDto);
        }
        return "redirect:/clientvendor/list";

    }




}
