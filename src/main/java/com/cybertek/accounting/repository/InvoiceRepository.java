package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    //is for main.html
    List<Invoice> findFirst3ByOrderByInsertDateTimeDesc();

    @Query(value = "SELECT * FROM Invoice i WHERE i.invoiceType=?1 ",nativeQuery = true)
    List<Invoice> findByStatus(Status status);


    List<Invoice> findAllByInvoice();
    Company findByCompanyId(String id);
    Boolean findAllByApproved();

    InvoiceType findAllByInvoiceTypeEquals(InvoiceType invoiceType);

    //List<SPTable> findAllByCompany(Company company);in the SPTRepo

}
