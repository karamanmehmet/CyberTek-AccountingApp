package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    Invoice findByInvoiceNoAndCompany(String invoiceNo, Company company);

    List<Invoice> findFirst3ByCompanyOrderByInvoiceDateDesc(Company company);

    List<Invoice> findFirst3ByCompanyOrderByInvoiceDateAsc(Company company);

    List<Invoice> findAllByCompanyOrderByInvoiceDateAsc(Company company);

    List<Invoice> findAllByCompanyAndInvoiceTypeOrderByInvoiceNo(Company company, InvoiceType invoiceType);

    List<Invoice> findAllByCompanyAndInvoiceStatus(Company company, InvoiceStatus invoiceStatus);

    List<Invoice> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company,InvoiceType invoiceType, InvoiceStatus invoiceStatus);

 }
