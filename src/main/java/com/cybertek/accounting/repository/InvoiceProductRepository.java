package com.cybertek.accounting.repository;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {


    List<InvoiceProduct> findByInvoice(Invoice invoice);

    List<InvoiceProduct> findByProduct(Product product);

    List<InvoiceProduct> findAllByInvoiceCompany(Company company);

    Optional<InvoiceProduct> findByInvoiceAndProductAndInvoiceCompany(Invoice invoice, Product product, Company company);

    //will do in future find by Company, Incovice Type / Status and Order By Invoice Date
    //anyone can do with Native / Query will be appreciated

    List<InvoiceProduct> findByInvoiceAndInvoiceCompany(Invoice invoice, Company company);

    @Query(" SELECT u FROM InvoiceProduct u Where u.invoice.company=:company and u.invoice.invoiceType=:invoiceType and u.invoice.invoiceStatus=:invoiceStatus order by u.invoice.invoiceDate asc")
    List<InvoiceProduct>  findByInvoiceStatusAndInvoiceTypeAndCompany(Company company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) ;

}
