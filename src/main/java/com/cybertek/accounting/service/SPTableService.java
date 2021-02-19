package com.cybertek.accounting.service;

import com.cybertek.accounting.dto.SPTableDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.enums.Status;

import java.util.List;

public interface SPTableService {

    SPTable create(SPTableDto spTable);

    List<SPTableDto> readAll();

    List<SPTableDto> readAllByCompany(Company company);

    List<SPTableDto> readAllByCompanyAndType(Company company,String type);

    List<SPTableDto> readAllByCompanyAndStatus(Company company,Status status);

    SPTable update(SPTableDto spTable);

    void delete(SPTableDto spTable);

    List<SPTableDto> readAllByCompanyAndStatusAndType(Company company, Status status,String type);


}

