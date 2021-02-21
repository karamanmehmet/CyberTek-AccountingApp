package com.cybertek.accounting.mapper;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

    private final CompanyRepository repository;

    public Company convertToEntity(CompanyDto dto) {

        return repository.findById(dto.getId()).get();

    }

    public CompanyDto convertToDto(Company obj) {

        return new CompanyDto(obj.getId(),obj.getTitle(),obj.getAddress1(),obj.getAddress2(),obj.getState(),obj.getRepresentative(),obj.getRepresentative(),obj.getEmail(),obj.getEstablishmentDate());
    }


}
