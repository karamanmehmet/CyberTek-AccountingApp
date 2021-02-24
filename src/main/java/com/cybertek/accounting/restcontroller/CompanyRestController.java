package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyRestController {

    CompanyService companyService;


    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) throws Exception {

        CompanyDto companyDto1 = companyService.create(companyDto);

        return ResponseEntity.ok(companyDto1);
    }

    @GetMapping("/companies")
    public List<CompanyDto> retrieveAllCompanies(){

        List<CompanyDto> allCompanyDtos = companyService.findAll();

        return allCompanyDtos;
    }

    @PutMapping("/update")
    public CompanyDto updateCompany(@RequestBody CompanyDto companyDto) throws Exception {

        CompanyDto updatedCompany = companyService.update(companyDto);

        return updatedCompany;
    }

    //TODO should we use find by id in service in order to create delete?
    @DeleteMapping
    public boolean deleteCompany(@RequestBody CompanyDto companyDto) throws Exception {
        return companyService.delete(companyDto);
    }

    @GetMapping("/{state}")
    public List<CompanyDto> findCompanyByState(@PathVariable("state") String state){
        return companyService.findByState(state);
    }




}
