package com.cybertek.accounting.repository;

import com.cybertek.accounting.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Long> {
}
