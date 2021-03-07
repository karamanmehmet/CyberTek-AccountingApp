package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.InvoiceMonetaryDetailDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.InvoiceProductRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.service.InvoiceMonetaryDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceMonetaryDetailServiceImpl implements InvoiceMonetaryDetailService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final CompanyRepository companyRepository;

    @Override
    public InvoiceMonetaryDetailDto create(InvoiceDto invoiceDto) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        double tax = 0;
        double cost = 0;
        double totalCost = 0;

        //TODO SecurityContextHolder

        Company foundCompany = companyRepository.findByEmail("karaman@crustycloud.com").orElseThrow(() -> new CompanyNotFoundException("This company does not exist"));

        Invoice invoice = invoiceRepository.findByInvoiceNoAndCompany(invoiceDto.getInvoiceNo(), foundCompany);

        if (invoice == null) {
            throw new InvoiceNotFoundException("No invoice found");
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoice(invoice);

        if (invoiceProductList.size() == 0) {
            return new InvoiceMonetaryDetailDto(0, 0, 0);
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
