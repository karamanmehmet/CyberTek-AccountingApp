package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.dto.ProductDto;
import com.cybertek.accounting.entity.*;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.*;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.*;
import com.cybertek.accounting.service.InvoiceProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final MapperGeneric mapper;
    private final HttpSession httpSession;

    @Override
    public InvoiceProductDto create(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException, InvoiceAlreadyApprovedException {

        Company foundCompany = getCompanyFromSecurity();
        Invoice foundInvoice = checkInvoice(invoiceProduct, foundCompany);
        Product foundProduct = checkProduct(invoiceProduct);
        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProductAndInvoiceCompany(foundInvoice, foundProduct, foundCompany).orElse(null);

        if (foundInvoiceProduct != null) {
            foundInvoiceProduct.setQty(foundInvoiceProduct.getQty() + invoiceProduct.getQty());
            foundInvoiceProduct.setUnitPrice(invoiceProduct.getUnitPrice());
            foundInvoiceProduct.setEnabled(true);
        } else {
            foundInvoiceProduct = mapper.convert(invoiceProduct, new InvoiceProduct());
            foundInvoiceProduct.setInvoice(foundInvoice);
            foundInvoiceProduct.setProduct(foundProduct);
            foundInvoiceProduct.setEnabled(true);
        }

        InvoiceProduct createdInvoiceProduct = invoiceProductRepository.saveAndFlush(foundInvoiceProduct);

        return mapper.convert(createdInvoiceProduct, new InvoiceProductDto());

    }

    @Override
    public InvoiceProductDto update(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException, InvoiceAlreadyApprovedException {

        Company foundCompany = getCompanyFromSecurity();
        Invoice foundInvoice = checkInvoice(invoiceProduct, foundCompany);
        Product foundProduct = checkProduct(invoiceProduct);
        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProductAndInvoiceCompany(foundInvoice, foundProduct, foundCompany).orElseThrow(() -> new InvoiceProductNotFoundException("No invoice product found"));

        foundInvoiceProduct = mapper.convert(invoiceProduct, new InvoiceProduct());
        foundInvoiceProduct.setEnabled(true);

        invoiceProductRepository.saveAndFlush(foundInvoiceProduct);

        return mapper.convert(foundInvoiceProduct, new InvoiceProductDto());
    }

    @Override
    public void delete(InvoiceProductDto invoiceProduct) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException, InvoiceAlreadyApprovedException {

        Company foundCompany = getCompanyFromSecurity();
        Invoice foundInvoice = checkInvoice(invoiceProduct, foundCompany);
        Product foundProduct = checkProduct(invoiceProduct);
        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProductAndInvoiceCompany(foundInvoice, foundProduct, foundCompany).orElseThrow(() -> new InvoiceProductNotFoundException("No invoice product found"));

        foundInvoiceProduct.setQty(0);
        foundInvoiceProduct.setUnitPrice(0);
        foundInvoiceProduct.setTax(0);
        foundInvoiceProduct.setEnabled(false);

        invoiceProductRepository.saveAndFlush(foundInvoiceProduct);
    }

    @Override
    public InvoiceProductDto findById(long id) throws InvoiceProductNotFoundException {
        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findById(id).orElseThrow(() -> new InvoiceProductNotFoundException("No InvoiceProduct Found"));
        return mapper.convert(foundInvoiceProduct, new InvoiceProductDto());
    }

    @Override
    public List<InvoiceProductDto> findAll() {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceCompanyOrderByInvoiceInvoiceNo(getCompanyFromSecurity());
        return invoiceProductList.stream().map(invoiceProduct -> {
            return mapper.convert(invoiceProduct, new InvoiceProductDto());
        }).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> findByInvoice(InvoiceDto invoiceDto) throws CompanyNotFoundException, InvoiceNotFoundException {

        Company company = getCompanyFromSecurity();
        Invoice foundInvoice = invoiceRepository.findByInvoiceNoAndCompany(invoiceDto.getInvoiceNo(), company);

        if (foundInvoice == null) {
            throw new InvoiceNotFoundException("No invoice found");
        }

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceAndInvoiceCompanyOrderById(foundInvoice, company);

        return invoiceProductList.stream().map(invoiceProduct -> {
            return mapper.convert(invoiceProduct, new InvoiceProductDto());
        }).collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDto findByInvoiceAndProduct(InvoiceDto invoice, ProductDto product) throws InvoiceProductNotFoundException, ProductNotFoundException, CompanyNotFoundException {

        Company foundCompany = getCompanyFromSecurity();
        Invoice foundInvoice = invoiceRepository.findByInvoiceNoAndCompany(invoice.getInvoiceNo(), foundCompany);
        Product foundProduct = productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException("No product found"));
        InvoiceProduct foundInvoiceProduct = invoiceProductRepository.findByInvoiceAndProductAndInvoiceCompany(foundInvoice, foundProduct, foundCompany).orElseThrow(() -> new InvoiceProductNotFoundException("No invoice product found"));

        return mapper.convert(foundInvoiceProduct, new InvoiceProductDto());
    }

    @Override
    public List<InvoiceProductDto> findByInvoiceStatusAndInvoiceTypeAndCompany(CompanyDto company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceStatusAndInvoiceTypeAndCompany(mapper.convert(company, new Company()), invoiceType, invoiceStatus);

        return invoiceProductList.stream().map(invoiceProduct -> {
            return mapper.convert(invoiceProduct, new InvoiceProductDto());
        }).collect(Collectors.toList());
    }

    @Override
    public void checkStocks(InvoiceDto invoiceDto) throws NotEnoughProductInStockException {

        Company company = getCompanyFromSecurity();
        Invoice invoice = invoiceRepository.findByInvoiceNoAndCompany(invoiceDto.getInvoiceNo(), company);
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceAndInvoiceCompanyOrderById(invoice, company);

        invoiceProductList.forEach(invoiceProduct -> {
            Product product = invoiceProduct.getProduct();
            if (product.getQty() < invoiceProduct.getQty() && invoice.getInvoiceType() == InvoiceType.SALES) {
                try {
                    throw new NotEnoughProductInStockException("Not enough product in the stock");
                } catch (NotEnoughProductInStockException e) {
                    e.printStackTrace();
                }
            } else if (product.getQty() >= invoiceProduct.getQty() && invoice.getInvoiceType() == InvoiceType.SALES) {
                product.setQty(product.getQty() - (invoiceProduct.getQty()));
                productRepository.saveAndFlush(product);
            } else {
                product.setQty(product.getQty() + (invoiceProduct.getQty()));
                productRepository.saveAndFlush(product);
            }
        });
    }

    @Override
    public Map<ProductDto, Queue<InvoiceProductDto>> findBySalesAndInvoiceStatus(InvoiceStatus invoiceStatus) throws CompanyNotFoundException {

        CompanyDto company = (CompanyDto) httpSession.getAttribute("company");
        Company foundCompany = companyRepository.findByEmail(company.getEmail()).orElseThrow(() -> new CompanyNotFoundException("No company found found"));

        List<InvoiceProductDto> salesList = convertToDto(invoiceProductRepository.findAllByInvoiceInvoiceTypeAndInvoiceCompanyAndInvoiceInvoiceStatusOrderByInvoiceInvoiceDate(InvoiceType.SALES, foundCompany, InvoiceStatus.APPROVED));

        //get all selled
        Map<ProductDto, Queue<InvoiceProductDto>> salesMap = new HashMap<>();

        for (InvoiceProductDto sales : salesList) {
            Queue<InvoiceProductDto> salesQueue = salesMap.getOrDefault(sales.getProduct(), new LinkedList<>());
            salesQueue.add(sales);
            salesMap.put(sales.getProduct(), salesQueue);
        }

        return salesMap;
    }

    @Override
    public Map<ProductDto, Queue<InvoiceProductDto>> findByPurchaseAndInvoiceStatus(InvoiceStatus invoiceStatus) throws CompanyNotFoundException {

        CompanyDto company = (CompanyDto) httpSession.getAttribute("company");
        Company foundCompany = companyRepository.findByEmail(company.getEmail()).orElseThrow(() -> new CompanyNotFoundException("No company found found"));

        List<InvoiceProductDto> purchaseList = convertToDto(invoiceProductRepository.findAllByInvoiceInvoiceTypeAndInvoiceCompanyAndInvoiceInvoiceStatusOrderByInvoiceInvoiceDate(InvoiceType.PURCHASE, foundCompany, InvoiceStatus.APPROVED));

        //get all purchased
        Map<ProductDto, Queue<InvoiceProductDto>> purchaseMap = new HashMap<>();

        for (InvoiceProductDto purchase : purchaseList) {
            Queue<InvoiceProductDto> purchaseQueue = purchaseMap.getOrDefault(purchase.getProduct(), new LinkedList<>());
            purchaseQueue.add(purchase);
            purchaseMap.put(purchase.getProduct(), purchaseQueue);
        }

        return purchaseMap;
    }

    private List<InvoiceProductDto> convertToDto(List<InvoiceProduct> list) {
        return list.stream().map(invoiceProduct -> {
            return mapper.convert(invoiceProduct, new InvoiceProductDto());
        }).collect(Collectors.toList());
    }

    private Invoice checkInvoice(InvoiceProductDto invoiceProductDto, Company company) throws InvoiceNotFoundException, InvoiceAlreadyApprovedException {

        Invoice foundInvoice = invoiceRepository.findByInvoiceNoAndCompany(invoiceProductDto.getInvoice().getInvoiceNo(), company);

        if (foundInvoice == null) {
            throw new InvoiceNotFoundException("No invoice found");
        } else if (foundInvoice.getInvoiceStatus() != InvoiceStatus.OPEN) {
            throw new InvoiceAlreadyApprovedException("This invoice already approved or archived, it is not possible to make changes on it");
        }
        return foundInvoice;
    }

    private Product checkProduct(InvoiceProductDto invoiceProductDto) throws ProductNotFoundException {
        return productRepository.findById(invoiceProductDto.getProduct().getId()).orElseThrow(() -> new ProductNotFoundException("No product found"));
    }

    private Company getCompanyFromSecurity() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        Company foundCompany = user.getCompany();

        return foundCompany;
    }

}
