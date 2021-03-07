package com.cybertek.accounting.restcontroller;

import com.cybertek.accounting.dto.InvoiceProductDto;
import com.cybertek.accounting.exception.*;
import com.cybertek.accounting.service.InvoiceProductService;
import com.cybertek.accounting.service.InvoiceService;
import com.cybertek.accounting.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoiceproduct")
public class InvoiceProductRestController {

    private final InvoiceProductService invoiceProductService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    @GetMapping("/all")
    public List<InvoiceProductDto> getAllInvoiceProducts() {
        return invoiceProductService.findAll();
    }

    @GetMapping
    public InvoiceProductDto getInvoiceProductByInvoiceAndProduct(@RequestParam long invoiceId, @RequestParam long productId) throws ProductNotFoundException, InvoiceNotFoundException, InvoiceProductNotFoundException, CompanyNotFoundException {
        return invoiceProductService.findByInvoiceAndProduct(invoiceService.findByIdDto(invoiceId), productService.findById(productId));
    }

    @PostMapping
    public InvoiceProductDto createInvoiceProduct(@RequestBody InvoiceProductDto invoiceProductDto) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException {
        return invoiceProductService.create(invoiceProductDto);
    }

    @PutMapping
    public InvoiceProductDto updateInvoiceProduct(@RequestBody InvoiceProductDto invoiceProductDto) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException {
        return invoiceProductService.update(invoiceProductDto);
    }

    @DeleteMapping
    public void deleteInvoiceProduct(@RequestBody InvoiceProductDto invoiceProductDto) throws InvoiceProductNotFoundException, InvoiceNotFoundException, ProductNotFoundException, NotEnoughProductInStockException, CompanyNotFoundException {
        invoiceProductService.delete(invoiceProductDto);
    }

}
