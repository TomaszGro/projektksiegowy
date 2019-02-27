package pl.tomaszgronski.projektksiegowy.invoice.db;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class InFileDatabase implements Database {

    private FileHelper fileHelper;

    @Autowired
    public InFileDatabase(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) throws IOException {
        fileHelper.saveInvoice(invoice);
        return invoice;
    }

    @Override
    public boolean deleteInvoice(Long invoiceId) {
        return fileHelper.deleteInvoice(invoiceId);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) throws IOException {
        fileHelper.updateInvoice(invoice);
        return invoice;
    }

    @Override
    public Optional<Invoice> findInvoice(Long invoiceId) {
        List<Invoice> allDataFromFile = fileHelper.getAllDataFromFile();
        Optional<Invoice> first = allDataFromFile.stream().filter(s -> s.getId().equals(invoiceId)).findFirst();
        return first;
    }

    @Override
    public List<Invoice> getAll() {
        return fileHelper.getAllDataFromFile();
    }
}
