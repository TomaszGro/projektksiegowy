package pl.tomaszgronski.projektksiegowy.invoice;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tomaszgronski.projektksiegowy.invoice.db.Database;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.service.InvoiceBook;
import pl.tomaszgronski.projektksiegowy.invoice.validator.InvoiceValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@SpringBootTest
@RunWith(SpringRunner.class)

public class InvoiceBookTestMock {

    private static final Logger LOGGER = Logger.getLogger(pl.tomaszgronski.projektksiegowy.invoice.InvoiceControllerTest.class.toString());


    @Test
    public void saveInvoiceTest() throws IOException {
        Invoice mockREquestInvoice = TestHelper.testInvoiceToSave("firma23", "9788222038", "firma4", "1247590186");
        Database database = Mockito.mock(Database.class);
        Invoice mockReturnInvoice = TestHelper.testInvoiceToSave("firma23", "9788222038", "firma4", "1247590186");
        mockReturnInvoice.setId(321l);

        Mockito.when(database.saveInvoice(mockREquestInvoice)).thenReturn(mockReturnInvoice);

        InvoiceBook invoiceBook = new InvoiceBook(database, new InvoiceValidator());
        Invoice savedInvoice = invoiceBook.saveInvoice(mockREquestInvoice);
        Assert.assertNotNull(savedInvoice.getId());
        Assert.assertEquals(Long.valueOf(321l), savedInvoice.getId());


    }

    @Test
    public void findInvoiceTest() throws IOException {
        Database database = Mockito.mock(Database.class);
        Invoice mockReturnInvoice = TestHelper.testInvoiceToSave("firma23", "9788222038", "firma4", "1247590186");
        mockReturnInvoice.setId(321l);

        Mockito.when(database.findInvoice(321l)).thenReturn(Optional.of(mockReturnInvoice));

        InvoiceBook invoiceBook = new InvoiceBook(database, new InvoiceValidator());
        Optional<Invoice> optional = invoiceBook.findInvoice(mockReturnInvoice.getId());
        Assert.assertNotNull(optional.get().getId());
        Assert.assertEquals(Long.valueOf(321l), optional.get().getId());
    }

    @Test
    public void getAllInvoiceTest() {
        Database database = Mockito.mock(Database.class);
        Invoice invoice1 = TestHelper.testInvoiceToSave("firma23", "9788222038", "firma4", "1247590186");
        invoice1.setId(321l);
        List<Invoice> mockInvoices = new ArrayList<>();
        mockInvoices.add(invoice1);
        Mockito.when(database.getAll()).thenReturn(mockInvoices);

        InvoiceBook invoiceBook = new InvoiceBook(database, new InvoiceValidator());
        List<Invoice> allInvoicesFromDB = invoiceBook.getAll();
        Assert.assertNotEquals(0, allInvoicesFromDB.size());


    }

}
