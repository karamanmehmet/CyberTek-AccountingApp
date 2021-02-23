package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.service.ClientVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cv")
public class ClientVendorRestController {

    private final ClientVendorService service;

    @GetMapping("/clientvendors")
    public List<ClientVendorDto> getAllClientVendor(){
        return  service.findAll();
    }
}
