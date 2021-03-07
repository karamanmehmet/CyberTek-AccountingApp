package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {


    List<InvoiceProduct> findByInvoice(Invoice invoice);

    List<InvoiceProduct> findByProduct(Product product);

    Optional<InvoiceProduct> findByInvoiceAndProduct(Invoice invoice, Product product);

    //will do in future find by Company, Incovice Type / Status and Order By Invoice Date
    //anyone can do with Native / Query will be appreciated

    List<InvoiceProduct> findByInvoiceAndInvoiceCompany(Invoice invoice, Company company);

}
