package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.ExistentClientVendorException;


import java.util.List;

public interface ClientVendorService {

    ClientVendorDto create(ClientVendorDto clientVendor) throws ExistentClientVendorException;

    List<ClientVendorDto> findAll();

    ClientVendorDto findByEmail(String email) throws ClientVendorNotFoundException;

    List<ClientVendorDto> findAllByCompany(CompanyDto company);

    List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, ClientVendorType type);

    List<ClientVendorDto> findAllByCompanyAndState(CompanyDto company, String state);

    ClientVendorDto update(ClientVendorDto clientVendor) throws ClientVendorNotFoundException;

    void delete(ClientVendorDto clientVendor) throws ClientVendorNotFoundException;


    List<ClientVendorDto> findAllByCompanyAndStateAndType(CompanyDto company, String state, ClientVendorType type);


}

