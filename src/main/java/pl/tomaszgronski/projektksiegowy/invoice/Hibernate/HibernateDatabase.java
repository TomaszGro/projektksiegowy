package pl.tomaszgronski.projektksiegowy.invoice.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.tomaszgronski.projektksiegowy.invoice.db.Database;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateDatabase implements Database {

    private InvoiceRepository invoiceRepository;

    @Autowired
    public HibernateDatabase(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public boolean deleteInvoice(Long invoiceId) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
        if (invoiceOptional.isPresent() == true) {
            Invoice invoice = invoiceOptional.get();
            invoiceRepository.delete(invoice);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> findInvoice(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }
}
