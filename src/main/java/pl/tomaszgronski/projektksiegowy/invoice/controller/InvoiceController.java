package pl.tomaszgronski.projektksiegowy.invoice.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.model.ValidatorExternal;
import pl.tomaszgronski.projektksiegowy.invoice.service.InvoiceBook;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Api


public class InvoiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceController.class.toString());

    private InvoiceBook invoiceBook;

//    @Autowired
//    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceController(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @PostMapping("/invoice")
    public Invoice saveInvoice(@RequestBody Invoice invoice) {

        LOGGER.info("saveInvoice");
        if (invoice.getFromCompany() == null || invoice.getFromCompany().getTaxIndetificationNumber() == null
                || ValidatorExternal.isValidNip(invoice.getFromCompany().getTaxIndetificationNumber()) == false) {
            LOGGER.info("saveInvoice()" + ValidatorExternal.isValidNip(invoice.getFromCompany().getTaxIndetificationNumber()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient data for Company");
        }
        LOGGER.info("invoice add:" + invoice.toString());
        if (invoice.getId() != null) {
            Optional<Invoice> invoice1 = invoiceBook.findInvoice(invoice.getId());
            if (invoice1.isPresent() == true) {
                LOGGER.info("invoked - with params " + invoice.toString());
                return null;
            }
        }
        try {
            invoiceBook.saveInvoice(invoice);
        } catch (IllegalArgumentException e) {
            LOGGER.error(invoice.toString(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient data for invoice");
        } catch (IOException e) {
            LOGGER.error(invoice.toString(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error please contact support department");
        }
        return invoice;
    }

    @DeleteMapping(value = "/invoice/{id}")
    public boolean deleteInvoice(@PathVariable(value = "id") Long invoiceId) {
        LOGGER.info("deleteInvoice");
        boolean deleted = invoiceBook.deleteInvoice(invoiceId);
        LOGGER.info("Invoice id:" + invoiceId + "was successfully: " + deleted);
        return deleted;
    }

    @PutMapping("/invoice/{id}")
    public Invoice updateInvoice(@RequestBody Invoice invoice, @PathVariable(value = "id") Long invoiceId) {
        LOGGER.info("updateInvoice");
        Optional<Invoice> invoice1 = invoiceBook.findInvoice(invoiceId);
        if (invoice1.isPresent() == true) {
            Invoice invoicedb = invoice1.get();
            if (invoicedb.getFromCompany().getTaxIndetificationNumber().equals(invoice.getFromCompany().getTaxIndetificationNumber()) == false) {
                LOGGER.warn("Wrong NIP number, NIP1: " + invoicedb.getFromCompany().getTaxIndetificationNumber() + " NIP 2 " + invoice.getFromCompany().getTaxIndetificationNumber() + "Company ID: " + invoice.getFromCompany().getId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong NIP number");
            }
        } else {
            LOGGER.warn("There is no invoice with given number");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no invoice with given number" + invoiceId);
        }
        try {
            return invoiceBook.updateInvoice(invoice, invoiceId).get();
        } catch (IOException e) {
            LOGGER.error(invoice.toString(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error please contact support department");
        }
    }

    @GetMapping("/invoice/{id}")
    public Invoice findInvoice(@PathVariable(value = "id") Long invoiceId) {
        LOGGER.info("findInvoice");
        return invoiceBook.findInvoice(invoiceId).get();
    }

    @GetMapping("/invoice")
    public List<Invoice> getallInvoice() {
        LOGGER.info("getallInvoice");
        return invoiceBook.getAll();
    }


}

