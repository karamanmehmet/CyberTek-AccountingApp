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

    List<SPTable> findAllByType(String type);

    List<SPTable> findAllByEmail(String email);

    List<SPTable> findAllByState(String state);

    List<SPTable> findAllByCompanyAndStatusAndType(Company company, Status status, String type);

    // What is the difference between state - boolean enable - boolean active ??


}
