package pl.tomaszgronski.projektksiegowy.invoice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import pl.tomaszgronski.projektksiegowy.invoice.controller.InvoiceController;
import pl.tomaszgronski.projektksiegowy.invoice.model.Company;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringRunner.class)


public class InvoiceControllerTest {

    private static final Logger LOGGER = Logger.getLogger(InvoiceControllerTest.class.toString());

    @Autowired
    private InvoiceController invoiceController;

    @Test
    public void saveInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma2");
        company.setTaxIndetificationNumber("5199919784");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        invoiceController.saveInvoice(invoice);

    }

    @Test
    public void deleteInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma2");
        company.setTaxIndetificationNumber("5222791329");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        Invoice invoice1 = invoiceController.saveInvoice(invoice);
        invoiceController.deleteInvoice(invoice1.getId());

    }

    @Test
    public void findInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma3");
        company.setTaxIndetificationNumber("1240936813");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        Invoice invoice1 = invoiceController.saveInvoice(invoice);
        invoiceController.findInvoice(invoice1.getId());


    }

    @Test
    public void getallInvoiceTest() {
        invoiceController.getallInvoice();

    }

    @Test

    public void updateInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma1");
        company.setTaxIndetificationNumber("4961128737");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        Invoice invoice1 = invoiceController.saveInvoice(invoice);

        Invoice updateInvoice = new Invoice();
        updateInvoice.setDate(ZonedDateTime.now());
        Company updateCompany = new Company();
        updateCompany.setCompanyName("firma2");
        updateCompany.setTaxIndetificationNumber("4961128737");
        updateInvoice.setFromCompany(updateCompany);
        List<InvoiceEntry> invoiceEntriesupdate = new ArrayList<>();
        InvoiceEntry invoiceEntryupdate = new InvoiceEntry();
        invoiceEntryupdate.setValueprice(new BigDecimal("100.99"));
        invoiceEntryupdate.setDescription("Cena");
        invoiceEntriesupdate.add(invoiceEntryupdate);
        updateInvoice.setInvoiceEntries(invoiceEntriesupdate);
        LOGGER.info("Invoice1.getID: " + invoice1.getId());
        invoiceController.updateInvoice(updateInvoice, invoice1.getId());

    }

    @Test(expected = ResponseStatusException.class)

    public void updateInvoiceWithWrongNipTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma1");
        company.setTaxIndetificationNumber("4961128737");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        Invoice invoice1 = invoiceController.saveInvoice(invoice);

        Invoice updateInvoice = new Invoice();
        updateInvoice.setDate(ZonedDateTime.now());
        Company updateCompany = new Company();
        updateCompany.setCompanyName("firma2");
        updateCompany.setTaxIndetificationNumber("4961128730");
        updateInvoice.setFromCompany(updateCompany);
        List<InvoiceEntry> invoiceEntriesupdate = new ArrayList<>();
        InvoiceEntry invoiceEntryupdate = new InvoiceEntry();
        invoiceEntryupdate.setValueprice(new BigDecimal("100.99"));
        invoiceEntryupdate.setDescription("Cena");
        invoiceEntriesupdate.add(invoiceEntryupdate);
        updateInvoice.setInvoiceEntries(invoiceEntriesupdate);
        invoiceController.updateInvoice(updateInvoice, invoice1.getId());


    }


}
