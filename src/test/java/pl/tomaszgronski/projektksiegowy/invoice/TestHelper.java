package pl.tomaszgronski.projektksiegowy.invoice;

import pl.tomaszgronski.projektksiegowy.invoice.model.Company;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.model.InvoiceEntry;
import pl.tomaszgronski.projektksiegowy.invoice.model.Vat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {


    public static Invoice testInvoiceToSave(String companyName, String taxIdentificationNumber, String toCompanyName, String toTaxIdentificationNumber) {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        invoice.setInvoiceId("55");
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setTaxIndetificationNumber(taxIdentificationNumber);
        company.setAdress("Grodzka 6");
        invoice.setFromCompany(company);
        Company toCompany = new Company();
        toCompany.setCompanyName(toCompanyName);
        toCompany.setTaxIndetificationNumber(toTaxIdentificationNumber);
        toCompany.setAdress("Grodzka 7");
        invoice.setToCompany(toCompany);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntry.setVatRate(Vat.VAT_23);
        invoiceEntry.setVatValue(new BigDecimal("23"));
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);

        return invoice;
    }
}
