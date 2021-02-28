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
    private final CompanyService companyService;

    @GetMapping("/list")
    public String showClientVendors(Model model) throws CompanyNotFoundException {
        // TODO This part will update according to valid user
        model.addAttribute("clientVendors",clientVendorService.findAllByCompany(companyService.findByEmail("karaman@crustycloud.com")));

        return "/clientvendor/vendor-client-list";

    }
    @GetMapping("/add")
    public String addClientVendorForm(Model model) {
        model.addAttribute("clientvendor", new ClientVendorDto());
        return "/clientvendor/vendor-client-add";
    }

    @PostMapping("/add")
    public String addClientVendor(@ModelAttribute("clientvendor") ClientVendorDto clientVendorDto) throws ClientVendorAlreadyExistException, CompanyNotFoundException {

        clientVendorService.create(clientVendorDto);
        return "redirect:/clientvendor/list";

    }
    //WILL BE UPDATE
    @DeleteMapping("/delete")
    public String deleteClientVendor(@ModelAttribute("clientvendor") ClientVendorDto clientVendorDto) throws ClientVendorNotFoundException {

        clientVendorService.delete(clientVendorDto);
        return "redirect:/clientvendor/list";

    }

    @GetMapping("/update/{id}")
    public String updateClientVendor(@PathVariable long id,Model model) throws ClientVendorNotFoundException {
        model.addAttribute("clientvendor",clientVendorService.findById(id));

        return "/clientvendor/vendor-client-update";

    }
    // NEED TO TALK ABOUT LOGIC
    @PostMapping("/update/{id}")
    public String updateProject(@PathVariable long id,@ModelAttribute("clientvendor") ClientVendorDto clientVendorDto,Model model) throws ClientVendorNotFoundException, CompanyNotFoundException {
        clientVendorService.update(clientVendorDto,id);
        return "redirect:/clientvendor/list";
    }

}
