package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.ClientVendor;
import com.cybertek.accounting.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {

    List<ClientVendor> findAllByCompany(Company company);

    List<ClientVendor> findAllByCompanyAndEnabled(Company company, boolean enabled);

    List<ClientVendor> findAllByCompanyAndType(Company company, ClientVendorType type);

    List<ClientVendor> findAllByCompanyAndTypeAndEnabled(Company company, ClientVendorType type, boolean enabled);

}
