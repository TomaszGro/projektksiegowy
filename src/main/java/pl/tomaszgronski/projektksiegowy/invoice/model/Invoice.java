package pl.tomaszgronski.projektksiegowy.invoice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table
public class Invoice implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "invoice_id")
    private String invoiceId;

    @Column(name = "payment_date")
    private ZonedDateTime date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_company_id")
    private Company fromCompany;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_company_id")
    private Company toCompany;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceEntry> invoiceEntries;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceId.equals(invoice.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Company getFromCompany() {
        return fromCompany;
    }

    public void setFromCompany(Company fromCompany) {
        this.fromCompany = fromCompany;
    }

    public Company getToCompany() {
        return toCompany;
    }

    public void setToCompany(Company toCompany) {
        this.toCompany = toCompany;
    }

    public List<InvoiceEntry> getInvoiceEntries() {
        return invoiceEntries;
    }

    public void setInvoiceEntries(List<InvoiceEntry> invoiceEntries) {
        this.invoiceEntries = invoiceEntries;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", date=" + date +
                ", fromCompany=" + fromCompany +
                ", toCompany=" + toCompany +
                ", invoiceEntries=" + invoiceEntries +
                '}';
    }

    public Invoice() {
    }
}
