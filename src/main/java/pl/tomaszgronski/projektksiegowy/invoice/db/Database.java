package pl.tomaszgronski.projektksiegowy.invoice.db;

import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Database {

    Invoice saveInvoice(Invoice invoice) throws IOException;

    boolean deleteInvoice(Long invoiceId);

    Invoice updateInvoice(Invoice invoice) throws IOException;

    public Optional<Invoice> findInvoice(Long invoiceId);

    public List<Invoice> getAll();

}
