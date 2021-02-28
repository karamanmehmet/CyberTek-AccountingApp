package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.ClientVendorDto;
import com.cybertek.accounting.enums.ClientVendorType;
import com.cybertek.accounting.exception.ClientVendorNotFoundException;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.ClientVendorAlreadyExistException;


import java.util.List;

public interface ClientVendorService {

    ClientVendorDto create(ClientVendorDto clientVendor) throws ClientVendorAlreadyExistException, CompanyNotFoundException;

    List<ClientVendorDto> findAll();


    ClientVendorDto findByEmailAndType(String email, ClientVendorType type) throws CompanyNotFoundException;

    ClientVendorDto findByEmail(String email) throws ClientVendorNotFoundException;

    ClientVendorDto findById(long id) throws ClientVendorNotFoundException;


    List<ClientVendorDto> findAllByCompany(CompanyDto company);

    List<ClientVendorDto> findAllByCompanyAndType(CompanyDto company, ClientVendorType type);

    List<ClientVendorDto> findAllByCompanyAndState(CompanyDto company, String state);

    ClientVendorDto update(ClientVendorDto clientVendor) throws ClientVendorNotFoundException, CompanyNotFoundException;
    ClientVendorDto update(ClientVendorDto clientVendor,long id) throws ClientVendorNotFoundException, CompanyNotFoundException;


    void delete(ClientVendorDto clientVendor) throws ClientVendorNotFoundException;


    List<ClientVendorDto> findAllByCompanyAndStateAndType(CompanyDto company, String state, ClientVendorType type);


}

