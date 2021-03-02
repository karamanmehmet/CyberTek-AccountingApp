package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.ClientVendor;
import com.cybertek.accounting.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor,Long> {

    Optional<ClientVendor> findByEmail(String email );

    Optional<ClientVendor> findById(long id );

    List<ClientVendor> findByEmailAndCompanyAndEnabled(String email,Company company,boolean enabled);

   // Optional<ClientVendor> findByEmailAndCompanyAndEnabled(String email,Company company,boolean enabled);

    Optional <ClientVendor> findByCompanyAndEmailAndType(Company company,String email,ClientVendorType type);

    List<ClientVendor> findAllByCompany(Company company);

    List<ClientVendor> findAllByCompanyAndEnabled(Company company, boolean enabled);

    List<ClientVendor> findAllByCompanyAndType(Company company, ClientVendorType type);

    List<ClientVendor> findAllByCompanyAndState(Company company, String state);

    List<ClientVendor> findAllByCompanyAndStateAndType(Company company, String state,ClientVendorType type);

    List<ClientVendor> findAllByCompanyAndTypeAndEnabled(Company company, ClientVendorType type, boolean enabled);

}
