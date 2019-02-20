package pl.tomaszgronski.projektksiegowy.invoice.validator;

import org.springframework.stereotype.Service;
import pl.tomaszgronski.projektksiegowy.invoice.model.Company;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.model.InvoiceEntry;

import java.util.List;

@Service

public class InvoiceValidator implements Validator<Invoice> {
    @Override
    public boolean validate(Invoice objectToValidate) {
        if (objectToValidate == null) {
            return false;
        }
        if (objectToValidate.getInvoiceId() == null) {
            return false;
        }
        if (objectToValidate.getDate() == null) {
            return false;
        }
        if (objectToValidate.getFromCompany() == null) {
            return false;
        }
        if (objectToValidate.getInvoiceEntries() == null) {
            return false;
        }
        if (objectToValidate.getToCompany() == null) {
            return false;
        }
        if (validateCompany(objectToValidate.getFromCompany()) == false) {
            return false;
        }
        if (validateCompany(objectToValidate.getToCompany()) == false) {
            return false;
        }
        if (validateInvoiceEntries(objectToValidate.getInvoiceEntries()) == false) {
            return false;
        }
        return true;
    }

    private boolean validateCompany(Company company) {

        if (company.getCompanyName() == null) {
            return false;
        }
        if (company.getTaxIndetificationNumber() == null) {
            return false;
        }
        if (company.getAdress() == null) {
            return false;
        }
        return true;
    }

    private boolean validateInvoiceEntries(List<InvoiceEntry> invoiceEntries) {
        for (InvoiceEntry invoiceEntry : invoiceEntries) {
            if (validateInvoiceEntry(invoiceEntry) == false) {
                return false;
            }
        }
        return true;
    }

    private boolean validateInvoiceEntry(InvoiceEntry invoiceEntry) {
        if (invoiceEntry.getDescription() == null) {
            return false;
        }
        if (invoiceEntry.getValueprice() == null) {
            return false;
        }
        if (invoiceEntry.getVatRate() == null) {
            return false;
        }
        if (invoiceEntry.getVatValue() == null) {
            return false;
        }

        return true;
    }
}
