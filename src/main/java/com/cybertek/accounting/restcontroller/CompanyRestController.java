package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.exception.CompanyAlreadyExistsException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyRestController {

    private final CompanyService companyService;


    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) throws CompanyAlreadyExistsException {

        CompanyDto companyDto1 = companyService.create(companyDto);

        return ResponseEntity.ok(companyDto1);
    }

    @GetMapping("/all")
    public List<CompanyDto> retrieveAllCompanies(){

        List<CompanyDto> allCompanyDtos = companyService.findAll();

        return allCompanyDtos;
    }

    @PutMapping
    public CompanyDto updateCompany(@RequestBody CompanyDto companyDto) throws CompanyNotFoundException {

        CompanyDto updatedCompany = companyService.update(companyDto);

        return updatedCompany;
    }


    @DeleteMapping
    public boolean deleteCompany(@RequestBody CompanyDto companyDto) throws CompanyNotFoundException {
        return companyService.delete(companyDto);
    }

    @GetMapping("/{state}")
    public List<CompanyDto> findCompanyByState(@PathVariable("state") String state){
        return companyService.findByState(state);
    }




}
