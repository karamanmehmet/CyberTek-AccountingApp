package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceNumberRepository extends JpaRepository<InvoiceNumber,Long> {

    List<InvoiceNumber> findInvoiceNumberByCompanyAndYear(Company company, int year);

    List<InvoiceNumber> findInvoiceNumberByCompany(Company company);

    @Query(value = "SELECT * FROM invoicenumber JOIN company ON invoicenumber.company_id = company.id ORDER BY invoicenumber DESC LIMIT 1", nativeQuery = true)
    InvoiceNumber findLastInvoiceNumberByCompany(Company company);

}
