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
        Invoice invoice = TestHelper.testInvoiceToSave("firma23", "9788222038", "firma4", "1247590186");
        invoiceController.saveInvoice(invoice);

    }

    @Test
    public void deleteInvoiceTest() {
        Invoice invoice = TestHelper.testInvoiceToSave("firma54", "9525074474", "firma4", "1247590186");
        Invoice invoice1 = invoiceController.saveInvoice(invoice);
        invoiceController.deleteInvoice(invoice1.getId());
    }

    @Test
    public void findInvoiceTest() {
        Invoice invoice = TestHelper.testInvoiceToSave("firma67", "8229812237", "firma4", "1247590186");
        Invoice invoice1 = invoiceController.saveInvoice(invoice);
        invoiceController.findInvoice(invoice1.getId());
    }

    @Test
    public void getAllInvoiceTest() {
        invoiceController.getallInvoice();
    }

    @Test
    public void updateInvoiceTest() {
        Invoice invoice = TestHelper.testInvoiceToSave("firma66", "9788222038", "firma4", "1247590186");
        Invoice invoice1 = invoiceController.saveInvoice(invoice);

        Invoice updateInvoice = new Invoice();
        updateInvoice.setDate(ZonedDateTime.now());
        Company updateCompany = new Company();
        updateCompany.setCompanyName("firma2");
        updateCompany.setTaxIndetificationNumber("9788222038");
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
        Invoice invoice = TestHelper.testInvoiceToSave("firma77", "1560002561", "firma4", "1247590186");
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
