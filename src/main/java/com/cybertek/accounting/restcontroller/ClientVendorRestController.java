package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ExistentClientVendorException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.service.ClientVendorService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cv")
public class ClientVendorRestController {

    private final ClientVendorService service;
    private final CompanyService companyService;

    /**
     * This method will be just testing purpose.
     * -----Will not use UI------
     * @return List<ClientVendorDto>
     */
    @GetMapping
    public List<ClientVendorDto> getAllClientVendor() {
        return service.findAll();
    }

    @PostMapping
    public ClientVendorDto createClientVendor(@RequestBody ClientVendorDto clientVendor) throws ExistentClientVendorException {

        return service.create(clientVendor);
    }

    @PutMapping
    public ClientVendorDto updateClientVendor(@RequestBody ClientVendorDto clientVendor) throws ClientVendorNotFoundException {

        return service.update(clientVendor);
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) throws ClientVendorNotFoundException {

        ClientVendorDto foundedCV = service.findByEmail(email);

        service.delete(foundedCV);

        System.out.println(foundedCV.getCompany()+" "+foundedCV.getType()+" "+foundedCV.getEmail()+" is deleted");
    }

    @GetMapping("/{email}")
    public ClientVendorDto findByEmail(@PathVariable String email) throws ClientVendorNotFoundException {
        return  service.findByEmail(email);
    }

    @GetMapping("/company/{email}")
    public List<ClientVendorDto> findCVByCompany(@PathVariable String email) throws CompanyNotFoundException {
        return  service.findAllByCompany(companyService.findByEmail(email));
    }

    @GetMapping("/company/{email}/{type}")
    public List<ClientVendorDto> findCVByCompanyAndType(@PathVariable ClientVendorType type,@PathVariable String email) throws CompanyNotFoundException {
        return service.findAllByCompanyAndType(companyService.findByEmail(email), type);

    }
    @GetMapping("/company/{email}/{state}")
    public List<ClientVendorDto> findCVByCompanyAndState(@PathVariable String state,@PathVariable String email) throws CompanyNotFoundException {
        return  service.findAllByCompanyAndState(companyService.findByEmail(email),state);
    }

    @GetMapping("/company/{email}/{state}/{type}")
    public List<ClientVendorDto> findCVByCompanyAndStateAndType(@PathVariable String email,@PathVariable String state,@PathVariable ClientVendorType type) throws CompanyNotFoundException {

        return service.findAllByCompanyAndStateAndType(companyService.findByEmail(email), state, type);

    }
}