package pl.tomaszgronski.projektksiegowy.invoice.db;

import org.springframework.stereotype.Repository;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository

public class InMemoryDatabase implements Database {

    private Map<Long, Invoice> map = new HashMap<>();

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        map.put(invoice.getId(), invoice);
        return invoice;
    }


    @Override
    public boolean deleteInvoice(Long invoiceId) {
        if (map.containsKey(invoiceId) == true) {
            map.remove(invoiceId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        map.put(invoice.getId(), invoice);
        return invoice;

    }

    @Override
    public Optional<Invoice> findInvoice(Long invoiceId) {
        if (map.containsKey(invoiceId) == true) {
            return Optional.of(map.get(invoiceId));

        } else {
            return Optional.empty();
        }

    }

    @Override
    public List<Invoice> getAll() {
        return null;
    }
}
