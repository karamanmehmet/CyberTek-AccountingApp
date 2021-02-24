package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.repository.InvoiceProductRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductRepository invoiceProductRepository;

    @Override
    public double calculateTax(InvoiceDto invoiceDto) throws Exception {

        double tax = 0;

        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceDto.getInvoiceNo());

        if (invoice == null) {
            throw new Exception("No invoice found");
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoice(invoice);

        if (invoiceProductList.size() == 0) {
            throw new Exception("No invoice product found");
        }

        for (InvoiceProduct invoiceProduct : invoiceProductList) {

            Product product = invoiceProduct.getProduct();
            tax += product.getTax() * invoiceProduct.getQty() * invoiceProduct.getUnitPrice();

        }

        return tax;

    }

    @Override
    public double calculateCost(InvoiceDto invoiceDto) throws Exception {

        double cost = 0;

        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceDto.getInvoiceNo());

        if (invoice == null) {
            throw new Exception("No invoice found");
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoice(invoice);

        if (invoiceProductList.size() == 0) {
            throw new Exception("No invoice product found");
        }

        for (InvoiceProduct invoiceProduct : invoiceProductList) {

            cost += invoiceProduct.getQty() * invoiceProduct.getUnitPrice();

        }

        return cost;

    }

    @Override
    public double calculateTotalCost(InvoiceDto invoiceDto) throws Exception {

        double cost = calculateCost(invoiceDto);
        double tax = calculateTax(invoiceDto);

        double totalCost = cost + tax;

        return totalCost;
    }

}
