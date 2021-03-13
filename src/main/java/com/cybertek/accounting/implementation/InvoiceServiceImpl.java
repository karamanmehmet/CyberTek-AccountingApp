package com.cybertek.accounting.implementation;

import com.cybertek.accounting.dto.CompanyDto;
import com.cybertek.accounting.dto.InvoiceDto;
import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.Invoice;
import com.cybertek.accounting.entity.InvoiceNumber;
import com.cybertek.accounting.entity.User;
import com.cybertek.accounting.enums.InvoiceStatus;
import com.cybertek.accounting.enums.InvoiceType;
import com.cybertek.accounting.exception.CompanyNotFoundException;
import com.cybertek.accounting.exception.InvoiceAlreadyExistsException;
import com.cybertek.accounting.exception.InvoiceNotFoundException;
import com.cybertek.accounting.exception.InvoiceProductNotFoundException;
import com.cybertek.accounting.mapper.MapperGeneric;
import com.cybertek.accounting.repository.CompanyRepository;
import com.cybertek.accounting.repository.InvoiceRepository;
import com.cybertek.accounting.repository.UserRepository;
import com.cybertek.accounting.service.CompanyService;
import com.cybertek.accounting.service.InvoiceMonetaryDetailService;
import com.cybertek.accounting.service.InvoiceNumberService;
import com.cybertek.accounting.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;
    private final MapperGeneric mapper;
    private final InvoiceNumberService invoiceNumberService;
    private final InvoiceMonetaryDetailService invoiceMonetaryDetailService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyService companyService;


    @Override
    public InvoiceDto create(InvoiceDto invoice) throws InvoiceAlreadyExistsException, CompanyNotFoundException, InvoiceNotFoundException, InvoiceProductNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        Company company = user.getCompany();

        invoice.setCompany(mapper.convert(company, new CompanyDto()));
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setInvoiceStatus(InvoiceStatus.OPEN);

        Invoice foundInvoice = repository.findByInvoiceNoAndCompany(invoice.getInvoiceNo(), company);

        if (foundInvoice != null) throw new InvoiceAlreadyExistsException("This invoice already exists");

        Invoice createdInvoice = mapper.convert(invoice, new Invoice());
        createdInvoice.setEnabled(true);

        InvoiceNumber createdInvoiceNumber = invoiceNumberService.create(invoice, invoice.getCompany());

        createdInvoice.setInvoiceNo(generateInvoiceNumber(createdInvoiceNumber));

        repository.saveAndFlush(createdInvoice);

        InvoiceDto invoiceDto = mapper.convert(createdInvoice, new InvoiceDto());

        return monetaryDetail(invoiceDto);
    }

    @Override
    public InvoiceDto update(InvoiceDto invoice) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        //TODO SecurityContextHolder
        Company foundCompany = companyRepository.findByEmail("karaman@crustycloud.com").orElseThrow(() -> new CompanyNotFoundException("This company does not exist"));

        Invoice foundInvoice = repository.findByInvoiceNoAndCompany(invoice.getInvoiceNo(), foundCompany);

        if (foundInvoice == null) throw new InvoiceNotFoundException("This invoice does not exist");

        Invoice updatedInvoice = mapper.convert(invoice, new Invoice());

        updatedInvoice.setId(foundInvoice.getId());
        updatedInvoice.setEnabled(foundInvoice.isEnabled());
        updatedInvoice.setInvoiceDate(foundInvoice.getInvoiceDate());
        updatedInvoice.setClientVendor(foundInvoice.getClientVendor());
        updatedInvoice.setCompany(foundInvoice.getCompany());
        updatedInvoice.setInvoiceType(foundInvoice.getInvoiceType());

        repository.saveAndFlush(updatedInvoice);

        return monetaryDetail(invoice);
    }

    @Override
    public boolean delete(String invoiceNo) throws InvoiceNotFoundException, CompanyNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);
        Company company = user.getCompany();

        Invoice foundInvoice = repository.findByInvoiceNoAndCompany(invoiceNo, company);

        if (foundInvoice == null) throw new InvoiceNotFoundException("This invoice does not exist");

        foundInvoice.setEnabled(false);
        foundInvoice.setInvoiceNo(invoiceNo + "-" + foundInvoice.getId());

        repository.saveAndFlush(foundInvoice);

        return !foundInvoice.isEnabled();
    }

    @Override
    public InvoiceDto approve(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {
        InvoiceDto foundedInvoiceDto = findByInvoiceNo(invoiceNo);
        foundedInvoiceDto.setInvoiceStatus(InvoiceStatus.APPROVED);
        update(foundedInvoiceDto);
        return foundedInvoiceDto;
    }

    @Override
    public InvoiceDto archive(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {
        InvoiceDto foundedInvoiceDto = findByInvoiceNo(invoiceNo);
        foundedInvoiceDto.setInvoiceStatus(InvoiceStatus.ARCHIVED);
        update(foundedInvoiceDto);
        return foundedInvoiceDto;
    }

    @Override
    public Invoice findById(long id) throws InvoiceNotFoundException {

        return repository.findById(id).orElseThrow(() -> new InvoiceNotFoundException("Invoice with this id can not be found!"));
    }

    @Override
    public InvoiceDto findByIdDto(long id) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice with this id can not be found!"));

        return monetaryDetail(mapper.convert(invoice, new InvoiceDto()));

    }

    @Override
    public InvoiceDto findByInvoiceNo(String invoiceNo) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        //TODO SecurityContextHolder

        Company company = companyRepository.findByEmail("karaman@crustycloud.com").orElseThrow(() -> new CompanyNotFoundException("Company not found"));

        Invoice invoice = repository.findByInvoiceNoAndCompany(invoiceNo, company);

        if (invoice == null) {
            throw new InvoiceNotFoundException("Invoice with this invoice number can not be found!");
        }

        return monetaryDetail(mapper.convert(invoice, new InvoiceDto()));
    }

    /**
     * Updated from Mehmet for invoices Chart
     */
    @Override
    public List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateAsc() throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        Company convertedCompany = mapper.convert(companyService.findByEmail("karaman@crustycloud.com"), new Company());

        List<InvoiceDto> invoiceDtoList = repository.findFirst3ByCompanyOrderByInvoiceDateAsc(convertedCompany).stream()
                .map(invoice -> mapper.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());

        return monetaryDetail(invoiceDtoList);
    }

    @Override
    public List<InvoiceDto> findFirst3ByCompanyOrderByInvoiceDateDesc(CompanyDto company) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        List<InvoiceDto> invoiceDtoList =  repository.findFirst3ByCompanyOrderByInvoiceDateDesc(mapper.convert(company, new Company())).stream()
                .map(invoice -> mapper.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());

        return monetaryDetail(invoiceDtoList);
    }

    @Override
    public List<InvoiceDto> findAllByCompanyAndInvoiceType(CompanyDto company, InvoiceType invoiceType) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        List<InvoiceDto> invoiceDtoList =  repository.findAllByCompanyAndInvoiceType(mapper.convert(company, new Company()), invoiceType).stream()
                .map(invoice -> mapper.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());

        return monetaryDetail(invoiceDtoList);
    }

    @Override
    public List<InvoiceDto> findAllByInvoiceType(InvoiceType invoiceType) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User loggedInUser = userRepository.findByEmail(email);
        Company company = loggedInUser.getCompany();

        List<Invoice> list = repository.findAllByCompanyAndInvoiceType(company, invoiceType);

        List<InvoiceDto> invoiceDtoList = list.stream().map(invoice -> {return mapper.convert(invoice, new InvoiceDto());}).collect(Collectors.toList());

        return monetaryDetail(invoiceDtoList);
    }

    @Override
    public List<InvoiceDto> findAllByCompanyAndInvoiceStatus(CompanyDto company, InvoiceStatus invoiceStatus) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        List<InvoiceDto> invoiceDtoList =  repository.findAllByCompanyAndInvoiceStatus(mapper.convert(company, new Company()), invoiceStatus).stream()
                .map(invoice -> mapper.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());

        return monetaryDetail(invoiceDtoList);
    }

    @Override
    public List<InvoiceDto> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(CompanyDto company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        List<InvoiceDto> invoiceDtoList = repository.findAllByCompanyAndInvoiceTypeAndInvoiceStatus(mapper.convert(company, new Company()), invoiceType, invoiceStatus).stream()
                .map(invoice -> mapper.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());

        return monetaryDetail(invoiceDtoList);
    }

    private List<InvoiceDto> monetaryDetail(List<InvoiceDto> invoiceDtoList) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        for (InvoiceDto invoice : invoiceDtoList) {
            invoice.setMonetaryDetailDto(invoiceMonetaryDetailService.create(invoice));
        }

        return invoiceDtoList;

    }

    private InvoiceDto monetaryDetail(InvoiceDto invoiceDto) throws InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {

        invoiceDto.setMonetaryDetailDto(invoiceMonetaryDetailService.create(invoiceDto));

        return invoiceDto;

    }

    private String generateInvoiceNumber(InvoiceNumber invoiceNumber) {

        String invoiceNo = String.valueOf(invoiceNumber.getInvoiceNumber());

        while (invoiceNo.length() < 3) {
            invoiceNo = "0".concat(invoiceNo);
        }

        String invoiceNumberString = "INV-" + invoiceNo;

        return invoiceNumberString;

    }

}
