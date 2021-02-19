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

    Optional<SPTable> findById(long id);

    List<SPTable> findAllByCompany(Company company);

    List<SPTable> findAllByCompanyAndEnabled(Company company,boolean enabled);

    List<SPTable> findAllByType(String type);

    List<SPTable> findAllByTypeAndEnabled(String type,boolean enabled);

    Optional<SPTable> findByEmail(String email);

    Optional<SPTable> findAllByEmailAndEnabled(String email,boolean enabled);

    List<SPTable> findAllByState(String state);

    List<SPTable> findAllByStateAndEnabled(String state,boolean enabled);

    List<SPTable> findAllByCompanyAndStatusAndType(Company company, Status status, String type);

    // What is the difference between state - boolean enable - boolean active ??


}
