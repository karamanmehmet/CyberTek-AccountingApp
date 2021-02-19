package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.entity.SPTable;
import com.cybertek.accounting.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {


    //Total amount directly will be under the impl class according to invoice id
    Optional<Invoice> findByInvoiceId(Long id);
    List<Product> findAllByProductStatus(Status status);
//
//    @Query(value = "SELECT * " +
//            " FROM invoice i " +
//            " WHERE i.invoiceDate=?1 and ",nativeQuery = true)
//    double totalpurchase(LocalDate invoiceDate);
////how to provide a month according to invoice date



}
