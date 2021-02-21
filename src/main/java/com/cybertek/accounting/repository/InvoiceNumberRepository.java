package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceNumberRepository extends JpaRepository<InvoiceNumber,Long> {

    InvoiceNumber findInvoiceNumberByCompanyAndYear(Company company,int year);
}
