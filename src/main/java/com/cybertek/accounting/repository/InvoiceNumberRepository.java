package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceNumberRepository extends JpaRepository<InvoiceNumber,Long> {

    List<InvoiceNumber> findInvoiceNumberByCompanyAndYear(Company company, int year);

    List<InvoiceNumber> findInvoiceNumberByCompany(Company company);

    Optional<InvoiceNumber> findFirstByCompanyOrderByInvoiceNumberDesc(Company company);

}
