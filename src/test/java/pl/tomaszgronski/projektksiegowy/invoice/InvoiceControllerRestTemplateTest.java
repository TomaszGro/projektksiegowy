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
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;

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
        Invoice invoice = TestHelper.testInvoiceToSave("firma17", "9512592534", "firma4", "1247590186");
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));
        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        Assert.assertNotNull(invoiceResponseEntity.getBody().getId());

    }

    @Test
    public void deleteInvoiceTest() {
        Invoice invoice = TestHelper.testInvoiceToSave("firma12", "5262621148", "firma4", "1247590186");
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));

        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice/", invoice, Invoice.class);
        Assert.assertNotNull(invoiceResponseEntity.getBody().getId());
        ResponseEntity<Boolean> deleteInvoice = testRestTemplate.exchange("http://localhost:" + port + "/invoice/" + invoiceResponseEntity.getBody().getId(), HttpMethod.DELETE, null, Boolean.class);

        Assert.assertEquals(true, deleteInvoice.getBody());
        Invoice invoiceResponseEntity1 = testRestTemplate.getForObject("http://localhost:" + port + "/invoice/" + invoiceResponseEntity.getBody().getId(), Invoice.class);

        Assert.assertNull(invoiceResponseEntity1.getId());
    }

    @Test
    public void findInvoiceTest() {
        Invoice invoice = TestHelper.testInvoiceToSave("firma19", "1152987805", "firma4", "1247590186");
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));

        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice/", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        Invoice invoiceResponseEntity1 = testRestTemplate.getForObject("http://localhost:" + port + "/invoice/" + invoiceResponseEntity.getBody().getId(), Invoice.class);

        Assert.assertNotNull(invoiceResponseEntity1.getId());

    }

    @Test
    public void getallInvoiceTest() {
        Invoice invoice = TestHelper.testInvoiceToSave("firma22", "5110303193", "firma4", "1247590186");
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("john123", "password"));

        ResponseEntity<Invoice> invoiceResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/invoice/", invoice, Invoice.class);
        LOGGER.info(invoiceResponseEntity.getBody().toString());
        Invoice[] forObject = testRestTemplate.getForObject("http://localhost:" + port + "/invoice", Invoice[].class);
        List<Invoice> invoices = Arrays.asList(forObject);
        LOGGER.info(invoices.toString());

        Assert.assertNotEquals(0, invoices.size());


    }
}


