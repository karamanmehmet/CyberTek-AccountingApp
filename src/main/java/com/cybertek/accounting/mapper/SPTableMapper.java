package com.cybertek.accounting.mapper;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.SPTableDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.repository.SPTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SPTableMapper {

    private final SPTableRepository repository;
    private final CompanyMapper companyMapper;

    public SPTable convertToEntity(SPTableDto dto) {

        return repository.findById(dto.getId()).get();

    }

    public SPTableDto convertToDto(SPTable obj) {

        return new SPTableDto(obj.getId(), obj.getCompanyName(), obj.getPhone(), obj.getEmail(), companyMapper.convertToDto(obj.getCompany()), obj.getZipCode(), obj.getAddress(), obj.getState(), obj.getType(), obj.isEnabled());

    }


}
