package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.InvoiceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceNumberRepository extends JpaRepository<InvoiceNumber,Long> {

    List<InvoiceNumber> findInvoiceNumberByCompanyAndYear(Company company, int year);

    List<InvoiceNumber> findInvoiceNumberByCompany(Company company);

    @Query(value = "SELECT * FROM invoicenumber i WHERE i.invoicenumber = CAST ((SELECT invoiceno FROM invoice i WHERE i.company_id = :id ORDER BY i.invoicedate DESC limit 1) AS INTEGER)", nativeQuery = true)
    InvoiceNumber findLastInvoiceNumberByCompanyId(@Param("id") long id);

}
