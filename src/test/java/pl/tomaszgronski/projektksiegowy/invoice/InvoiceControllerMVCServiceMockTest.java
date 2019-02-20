package pl.tomaszgronski.projektksiegowy.invoice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;
import pl.tomaszgronski.projektksiegowy.invoice.service.InvoiceBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class InvoiceControllerMVCServiceMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceBook invoiceBook;

    @Test
    @WithMockUser(username = "john123", password = "password", roles = "USER")

    public void findInvoiceTest() {

        Invoice invoice = TestHelper.testInvoiceToSave("firma15", "3559196448", "firma4", "1247590186");
        invoice.setId(123l);
        Mockito.when(invoiceBook.findInvoice(123l)).thenReturn(Optional.of(invoice));
        try {
            this.mockMvc.perform(get("/invoice/123")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(123l));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    @WithMockUser(username = "john123", password = "password", roles = "USER")

    public void getallInvoiceTest() {

        List<Invoice> invoices = new ArrayList<>();

        for (long i = 1; i < 6; i++) {
            Invoice invoice = TestHelper.testInvoiceToSave("firma20" + i, "1258673964", "firma4", "1247590186");
            invoices.add(invoice);
        }

        Mockito.when(invoiceBook.getAll()).thenReturn(invoices);
        try {
            this.mockMvc.perform(get("/invoice")).andDo(print()).andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


