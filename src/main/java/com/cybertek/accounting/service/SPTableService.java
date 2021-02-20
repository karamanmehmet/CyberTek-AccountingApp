package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.SPTableDto;


import java.util.List;

public interface SPTableService {

    SPTableDto create(SPTableDto spTable);

    List<SPTableDto> findAll();

    List<SPTableDto> findAllByCompany(CompanyDto company);

    List<SPTableDto> findAllByCompanyAndType(CompanyDto company, String type);

    List<SPTableDto> findAllByCompanyAndState(CompanyDto company,String state);

    SPTableDto update(SPTableDto spTable);

    void delete(SPTableDto spTable);

    List<SPTableDto> findAllByCompanyAndStateAndType(CompanyDto company,String state,String type);


}

