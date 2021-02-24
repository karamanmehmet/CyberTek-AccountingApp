package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.enums.ClientVendorType;
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


    @GetMapping
    public List<ClientVendorDto> getAllClientVendor() {
        return service.findAll();
    }

    @PostMapping("/create")
    public ClientVendorDto createClientVendor(@RequestBody ClientVendorDto clientVendor) throws Exception {

        return service.create(clientVendor);
    }

    @PutMapping("/update")
    public ClientVendorDto updateClientvendor(@RequestBody ClientVendorDto clientVendor) throws Exception {

        return service.update(clientVendor);
    }

    @DeleteMapping("/delete/{email}")
    public void delete(@PathVariable("email") String email) throws Exception {

        ClientVendorDto foundedCV = service.findByEmail(email);

        service.delete(foundedCV);

        System.out.println(foundedCV.getCompany()+" "+foundedCV.getType()+" "+foundedCV.getEmail()+" is deleted");
    }

    @GetMapping("/{email}")
    public ClientVendorDto findByEmail(@PathVariable("email") String email) throws Exception {
        return  service.findByEmail(email);
    }






    // TODO Company returns null so I can not try after this part

    @GetMapping("/company/{email}")
    public List<ClientVendorDto> findCVByCompany(@PathVariable("email") String email) throws Exception {
        return  service.findAllByCompany(companyService.findByEmail(email));
    }

    @GetMapping("/company/{email}/{type}")
    public List<ClientVendorDto> findCVByCompanyAndType(@PathVariable("type") String type,@PathVariable("email") String email) throws Exception {

        List<ClientVendorDto> allByCompanyAndType=new ArrayList<>();

        if (type.equalsIgnoreCase("vendor") ) {
            allByCompanyAndType = service.findAllByCompanyAndType(companyService.findByEmail(email), ClientVendorType.VENDOR);
        }

        if (type.equalsIgnoreCase("client") )
            allByCompanyAndType= service.findAllByCompanyAndType(companyService.findByEmail(email), ClientVendorType.CLIENT);

        return allByCompanyAndType;
    }
    @GetMapping("/company/{email}/{state}")
    public List<ClientVendorDto> findCVByCompanyAndState(@PathVariable("state") String state,@PathVariable("email") String email) throws Exception {
        return  service.findAllByCompanyAndState(companyService.findByEmail(email),state);
    }

    @GetMapping("/company/{email}/{state}/{type}")
    public List<ClientVendorDto> findCVByCompanyAndStateAndType(@PathVariable("email") String email,@PathVariable("state") String state,@PathVariable("type") String type) throws Exception {
        List<ClientVendorDto> allByCompanyAndStateAndType=new ArrayList<>();

        if (type.equalsIgnoreCase("vendor") )
            allByCompanyAndStateAndType = service.findAllByCompanyAndStateAndType(companyService.findByEmail(email), state, ClientVendorType.VENDOR);
        if (type.equalsIgnoreCase("client") )
            allByCompanyAndStateAndType = service.findAllByCompanyAndStateAndType(companyService.findByEmail(email), state, ClientVendorType.CLIENT);

        return allByCompanyAndStateAndType;
    }
}