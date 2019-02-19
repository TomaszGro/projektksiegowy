package pl.tomaszgronski.projektksiegowy.invoice.hibernate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomaszgronski.projektksiegowy.invoice.model.Invoice;


@Repository

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
