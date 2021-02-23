package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.ClientVendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cv")
public class ClientVendorRestController {

    private final ClientVendorService service;
    private final MapperGeneric mapper;

    @GetMapping("/clientvendors")
    public List<ClientVendorDto> getAllClientVendor(){
        return  service.findAll();
    }

    @PostMapping("/create")
    public ClientVendorDto createClientvendor(@RequestBody ClientVendorDto clientVendor) throws Exception {

        return mapper.convert(service.create(clientVendor),new ClientVendorDto());


    }
    @PutMapping("/update")
    public ClientVendorDto updateClientvendor(@RequestBody ClientVendorDto clientVendor) throws Exception {
        return mapper.convert(service.update(clientVendor),new ClientVendorDto());
        }}