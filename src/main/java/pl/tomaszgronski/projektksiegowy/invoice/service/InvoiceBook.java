package pl.tomaszgronski.projektksiegowy.invoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomaszgronski.projektksiegowy.invoice.db.Database;
import pl.tomaszgronski.projektksiegowy.invoice.model.Company;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.validator.Validator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service

public class InvoiceBook {

    private Database database;

    private Validator<Invoice> invoiceValidator;


    @Autowired
    public InvoiceBook(Database database, Validator<Invoice> invoiceValidator) {
        this.database = database;
        this.invoiceValidator = invoiceValidator;
    }


    public Invoice saveInvoice(Invoice invoice) throws IOException {
        if (invoiceValidator.validate(invoice)) {
            return database.saveInvoice(invoice);
        }
        throw new IllegalArgumentException("The invoice you provided is invalid");
    }

    public boolean deleteInvoice(Long invoiceId) {
        return database.deleteInvoice(invoiceId);

    }

    public Optional<Invoice> updateInvoice(Invoice invoice, Long invoiceId) throws IOException {
        Optional<Invoice> optionalInvoice = database.findInvoice(invoiceId);
        if (optionalInvoice.isPresent() == true) {
            Invoice invoiceDB = optionalInvoice.get();
            invoiceDB.setDate(invoice.getDate());
            Company updateCompanyFromDB = invoiceDB.getFromCompany();
            updateCompanyFromDB.setCompanyName(invoice.getFromCompany().getCompanyName());
            invoiceDB.setFromCompany(updateCompanyFromDB);
            invoiceDB.setToCompany(invoice.getToCompany());
            invoiceDB.setInvoiceId(invoice.getInvoiceId());
            invoiceDB.setInvoiceEntries(invoice.getInvoiceEntries());
            return Optional.of(database.updateInvoice(invoiceDB));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Invoice> findInvoice(Long invoiceId) {
        return database.findInvoice(invoiceId);
    }

    public List<Invoice> getAll() {
        return database.getAll();
    }
}
