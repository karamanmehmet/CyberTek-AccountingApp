package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.exception.CompanyAlreadyExistsException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private  final CompanyRepository companyRepository;
    private  final MapperGeneric mapperGeneric;


    @Override
    public CompanyDto findByEmail(String email) throws CompanyNotFoundException {
        Company foundCompany = companyRepository.findByEmail(email)
                .orElseThrow(() -> new CompanyNotFoundException("This company does not exist!"));
        return mapperGeneric.convert(foundCompany,new CompanyDto());
    }

    @Override
    public CompanyDto create(CompanyDto companyDto) throws CompanyAlreadyExistsException {

        Optional<Company> foundCompany = companyRepository.findByEmail(companyDto.getEmail());

        if (foundCompany.isPresent()) throw new CompanyAlreadyExistsException("This company already exists");

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
    public CompanyDto update(CompanyDto companyDto) throws CompanyNotFoundException {

        Company foundCompany = companyRepository.findByEmail(companyDto.getEmail())
                .orElseThrow(() -> new CompanyNotFoundException("This company does not exist"));

        companyRepository.saveAndFlush(foundCompany);

        return companyDto;
    }

    @Override
    public boolean delete(CompanyDto companyDto) throws CompanyNotFoundException {

        Company foundCompany = companyRepository.findByEmail(companyDto.getEmail())
                .orElseThrow(() -> new CompanyNotFoundException("This company does not exist"));

        foundCompany.setEnabled(false);

        companyRepository.saveAndFlush(foundCompany);
        return !foundCompany.isEnabled();
    }

    @Override
    public List<CompanyDto> findByState(String state) {

        return companyRepository.findByState(state).stream()
                .map(company -> mapperGeneric.convert(company,new CompanyDto()))
                .collect(Collectors.toList());
    }
}
