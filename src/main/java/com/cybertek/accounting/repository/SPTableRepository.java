package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.SPTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SPTableRepository extends JpaRepository<SPTable,Long> {


    List<SPTable> findAllByCompany(Company company);

    List<SPTable> findAllByCompanyAndEnabled(Company company,boolean enabled);

    List<SPTable> findAllByType(String type);

    List<SPTable> findAllByTypeAndEnabled(String type,boolean enabled);
    // I dont know do we need ?
    List<SPTable> findAllByCompanyAndStateAndType(Company company, String state, String type);



}
