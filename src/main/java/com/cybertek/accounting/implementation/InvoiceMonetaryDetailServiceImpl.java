package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.InvoiceMonetaryDetailDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.repository.InvoiceProductRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.InvoiceMonetaryDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceMonetaryDetailServiceImpl implements InvoiceMonetaryDetailService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductRepository invoiceProductRepository;

    @Override
    public InvoiceMonetaryDetailDto create(InvoiceDto invoiceDto) throws InvoiceNotFoundException, InvoiceProductNotFoundException {

        double tax = 0;
        double cost = 0;
        double totalCost = 0;

        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceDto.getInvoiceNo());

        if (invoice == null) {
            throw new InvoiceNotFoundException("No invoice found");
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoice(invoice);

        if (invoiceProductList.size() == 0) {
            throw new InvoiceProductNotFoundException("No invoice product found");
        }

        for (InvoiceProduct invoiceProduct : invoiceProductList) {
            tax += (invoiceProduct.getTax() / 100) * invoiceProduct.getQty() * invoiceProduct.getUnitPrice();
        }

        for (InvoiceProduct invoiceProduct : invoiceProductList) {
            cost += invoiceProduct.getQty() * invoiceProduct.getUnitPrice();
        }

        // Ask about rounding.

        tax = BigDecimal.valueOf(tax).setScale(2, RoundingMode.HALF_UP).doubleValue();
        cost = BigDecimal.valueOf(cost).setScale(2, RoundingMode.HALF_UP).doubleValue();

        totalCost = cost + tax;

        totalCost = BigDecimal.valueOf(totalCost).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return new InvoiceMonetaryDetailDto(tax, cost, totalCost);

    }

}
