package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Role;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SPTableRepository extends JpaRepository<SPTable,Long> {


    List<SPTable> findAllByCompany(Company company);

    List<SPTable> findAllByCompanyAndEnabled(Company company,boolean enabled);

    List<SPTable> findAllByType(String type);

    List<SPTable> findAllByTypeAndEnabled(String type,boolean enabled);

    List<SPTable> findAllByCompanyAndStatusAndType(Company company, Status status, String type);



}
