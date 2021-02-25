package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceProduct;
import com.cybertek.accounting.entity.Product;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.InvoiceProductRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.repository.ProductRepository;
import com.cybertek.accounting.service.InvoiceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final MapperGeneric mapper;

    @Override
    public InvoiceProductDto create(InvoiceProductDto invoiceProduct) throws Exception {

        Invoice foundInvoice = invoiceRepository.findByInvoiceNo(invoiceProduct.getInvoice().getInvoiceNo());

        if (foundInvoice == null) {
            throw new Exception("No invoice found");
        }

        Product foundProduct = productRepository.findById(invoiceProduct.getProduct().getId()).orElseThrow(() -> new Exception("No product found"));

        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProduct(foundInvoice, foundProduct).orElse(null);

        if (foundProduct.getQty() < invoiceProduct.getQty() && foundInvoice.getInvoiceType() == InvoiceType.SALES) {
            throw new Exception("Not enough product in the stock");
        }else if (foundProduct.getQty() >= invoiceProduct.getQty() && foundInvoice.getInvoiceType() == InvoiceType.SALES){
            foundProduct.setQty(foundProduct.getQty() - invoiceProduct.getQty());
        }else {
            foundProduct.setQty(foundProduct.getQty() + invoiceProduct.getQty());
        }

        productRepository.saveAndFlush(foundProduct);

        if (foundInvoiceProduct != null) {
            foundInvoiceProduct.setQty(foundInvoiceProduct.getQty() + invoiceProduct.getQty());
        } else {

            foundInvoiceProduct = new InvoiceProduct();

            foundInvoiceProduct.setProduct(foundProduct);
            foundInvoiceProduct.setInvoice(foundInvoice);

            foundInvoiceProduct.setQty(invoiceProduct.getQty());
            foundInvoiceProduct.setUnitPrice(foundProduct.getPrice());

        }

        InvoiceProduct createdInvoiceProduct = invoiceProductRepository.saveAndFlush(foundInvoiceProduct);

        return mapper.convert(createdInvoiceProduct, new InvoiceProductDto());

    }

    @Override
    public InvoiceProductDto update(InvoiceProductDto invoiceProduct) throws Exception {

        Invoice foundInvoice = invoiceRepository.findByInvoiceNo(invoiceProduct.getInvoice().getInvoiceNo());

        if (foundInvoice == null) {
            throw new Exception("No invoice found");
        }

        Product foundProduct = productRepository.findById(invoiceProduct.getProduct().getId()).orElseThrow(() -> new Exception("No product found"));

        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProduct(foundInvoice, foundProduct).orElseThrow(() -> new Exception("No invoice product found"));

        if (foundProduct.getQty() < invoiceProduct.getQty() - foundInvoiceProduct.getQty() && foundInvoice.getInvoiceType() == InvoiceType.SALES) {
            throw new Exception("Not enough product in the stock");
        }else if (foundProduct.getQty() >= invoiceProduct.getQty() - foundInvoiceProduct.getQty() && foundInvoice.getInvoiceType() == InvoiceType.SALES) {
            foundProduct.setQty(foundProduct.getQty() - (invoiceProduct.getQty() - foundInvoiceProduct.getQty()));
        }else {
            foundProduct.setQty(foundProduct.getQty() + (invoiceProduct.getQty() - foundInvoiceProduct.getQty()));
        }

        productRepository.saveAndFlush(foundProduct);

        foundInvoiceProduct.setQty(invoiceProduct.getQty());
        foundInvoiceProduct.setUnitPrice(foundProduct.getPrice());

        invoiceProductRepository.saveAndFlush(foundInvoiceProduct);

        return mapper.convert(foundInvoiceProduct, new InvoiceProductDto());

    }

    @Override
    public void delete(InvoiceProductDto invoiceProduct) throws Exception {

        Invoice foundInvoice = invoiceRepository.findByInvoiceNo(invoiceProduct.getInvoice().getInvoiceNo());

        if (foundInvoice == null) {
            throw new Exception("No invoice found");
        }

        Product foundProduct = productRepository.findById(invoiceProduct.getProduct().getId()).orElseThrow(() -> new Exception("No product found"));

        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProduct(foundInvoice, foundProduct).orElseThrow(() -> new Exception("No invoice product found"));

        if (foundInvoice.getInvoiceType() == InvoiceType.SALES) {
            foundProduct.setQty(foundProduct.getQty() + foundInvoiceProduct.getQty());
        }else if (foundInvoice.getInvoiceType() == InvoiceType.PURCHASE && foundInvoiceProduct.getQty() < foundProduct.getQty()) {
            foundProduct.setQty(foundProduct.getQty() - foundInvoiceProduct.getQty());
        }else {
            throw new Exception("Not enough product in the stock");
        }

        productRepository.saveAndFlush(foundProduct);

        foundInvoiceProduct.setQty(0);
        foundInvoiceProduct.setUnitPrice(0);
        foundInvoiceProduct.setDeleted(true);

        productRepository.saveAndFlush(foundProduct);
        invoiceProductRepository.saveAndFlush(foundInvoiceProduct);

    }

    @Override
    public List<InvoiceProductDto> findAll() {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAll();

        List<InvoiceProductDto> dtos = invoiceProductList.stream().map(invoiceProduct -> {return mapper.convert(invoiceProduct, new InvoiceProductDto());}).collect(Collectors.toList());

        return dtos;
    }

    @Override
    public InvoiceProductDto findByInvoiceAndProduct(InvoiceDto invoice, ProductDto product) throws Exception {

        Invoice foundInvoice = invoiceRepository.findByInvoiceNo(invoice.getInvoiceNo());
        Product foundProduct = productRepository.findById(product.getId()).orElseThrow(() -> new Exception("No product found"));

        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProduct(foundInvoice, foundProduct).orElseThrow(() -> new Exception("No invoice product found"));

        return mapper.convert(foundInvoiceProduct, new InvoiceProductDto());

    }

}
