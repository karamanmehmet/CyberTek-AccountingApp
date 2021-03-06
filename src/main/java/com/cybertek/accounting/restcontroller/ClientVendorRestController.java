package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ClientVendorAlreadyExistException;
import com.cybertek.accounting.service.ClientVendorService;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<ClientVendorDto> getAllClientVendor() throws CompanyNotFoundException {
        return service.findAll();
    }

    @PostMapping
    public ClientVendorDto createClientVendor(@RequestBody ClientVendorDto clientVendor) throws ClientVendorAlreadyExistException, CompanyNotFoundException {

        return service.create(clientVendor);
    }

    @PutMapping
    public ClientVendorDto updateClientVendor(@RequestBody ClientVendorDto clientVendor) throws ClientVendorNotFoundException, CompanyNotFoundException {

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
        return  service.findAllByState(state);
    }

    @GetMapping("/company/{email}/{state}/{type}")
    public List<ClientVendorDto> findCVByCompanyAndStateAndType(@PathVariable String email,@PathVariable String state,@PathVariable ClientVendorType type) throws CompanyNotFoundException {

        return service.findAllByStateAndType(state, type);

    }
}