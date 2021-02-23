package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    CompanyRepository companyRepository;
    MapperGeneric mapperGeneric;

    @Override
    public CompanyDto create(CompanyDto companyDto) throws Exception {

        //TODO discuss if findByEmail is valid | email is unique in this case
        //TODO discuss if we should use Optional<>

        Optional<Company> foundCompany = companyRepository.findByEmail(companyDto.getEmail());

        if (foundCompany.isPresent()) throw new Exception("This company already exists");

        companyRepository.saveAndFlush(mapperGeneric.convert(companyDto,new Company()));

        return companyDto;
    }

    @Override
    public List<CompanyDto> findAll() {
        return companyRepository.findAll().stream()
                .map(company -> mapperGeneric.convert(company,new CompanyDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto update(CompanyDto companyDto) throws Exception {

        Company foundCompany = companyRepository.findByEmail(companyDto.getEmail())
                .orElseThrow(() -> new Exception("This company does not exist"));

        companyRepository.saveAndFlush(foundCompany);

        return companyDto;
    }

    @Override
    public void delete(CompanyDto companyDto) throws Exception {

        //TODO discuss delete logic|change the unique part after setting enabled=false

        Company foundCompany = companyRepository.findByEmail(companyDto.getEmail())
                .orElseThrow(() -> new Exception("This company does not exist"));

        foundCompany.setEnabled(false);
        foundCompany.setEmail(companyDto.getEmail() + "-" + foundCompany.getId());

        companyRepository.saveAndFlush(foundCompany);
    }

    @Override
    public List<CompanyDto> findByState(String state) {

        return companyRepository.findByState(state).stream()
                .map(company -> mapperGeneric.convert(company,new CompanyDto()))
                .collect(Collectors.toList());
    }
}
