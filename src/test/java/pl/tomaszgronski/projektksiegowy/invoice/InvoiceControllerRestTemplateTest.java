package pl.tomaszgronski.projektksiegowy.invoice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tomaszgronski.projektksiegowy.invoice.model.Company;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.model.InvoiceEntry;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class InvoiceControllerRestTemplateTest {

    private static final Logger LOGGER = Logger.getLogger(InvoiceControllerRestTemplateTest.class.toString());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void saveInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma4");
        company.setTaxIndetificationNumber("1111082579");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));
        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        Assert.assertNotNull(invoiceResponseEntity.getBody().getId());

    }

    @Test
    public void deleteInvoiceTest() {

        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma5");
        company.setTaxIndetificationNumber("5243652805");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));
        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice/", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        ResponseEntity<Boolean> deleteInvoice = testRestTemplate.exchange("http://localhost:" + port + "/invoice/" + invoiceResponseEntity.getBody().getId(), HttpMethod.DELETE, null, Boolean.class);
        Assert.assertEquals(true, deleteInvoice.getBody());
    }

    @Test
    public void findInvoiceTest() {

        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma6");
        company.setTaxIndetificationNumber("5114713611");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));
        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice/", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        Invoice invoiceResponseEntity1 = testRestTemplate.getForObject("http://localhost:" + port + "/invoice/" + invoiceResponseEntity.getBody().getId(), Invoice.class);
        Assert.assertNotNull(invoiceResponseEntity1.getId());

    }

    @Test
    public void getallInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setDate(ZonedDateTime.now());
        Company company = new Company();
        company.setCompanyName("firma6");
        company.setTaxIndetificationNumber("5114713611");
        invoice.setFromCompany(company);
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        InvoiceEntry invoiceEntry = new InvoiceEntry();
        invoiceEntry.setValueprice(new BigDecimal("100.99"));
        invoiceEntry.setDescription("Cena");
        invoiceEntries.add(invoiceEntry);
        invoice.setInvoiceEntries(invoiceEntries);
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));
        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice/", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        Invoice[] forObject = testRestTemplate.getForObject("http://localhost:" + port + "/invoice", Invoice[].class);
        List<Invoice> invoices = Arrays.asList(forObject);
        LOGGER.info(invoices.toString());
        Assert.assertNotEquals(0, invoices.size());


    }
}


