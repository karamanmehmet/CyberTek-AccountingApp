package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SPTableRepository extends JpaRepository<SPTable,Long> {

    List<SPTable> findAllByCompany(Company company);

    List<SPTable> findAllByCompanyAndEnabled(Company company,boolean enabled);

    List<SPTable> findAllByCompanyAndType(Company company, ClientVendorType type);

    List<SPTable> findAllByCompanyAndTypeAndEnabled(Company company, ClientVendorType type, boolean enabled);

}
