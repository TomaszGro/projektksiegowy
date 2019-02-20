package pl.tomaszgronski.projektksiegowy.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table


public class InvoiceEntry implements Serializable {
    public InvoiceEntry() {
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "value_price")
    private BigDecimal valueprice;
    @Column(name = "vat_value")
    private BigDecimal vatValue;
    @Column(name = "vat_rate")
    @Enumerated(EnumType.STRING)
    private Vat vatRate;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntry that = (InvoiceEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValueprice() {
        return valueprice;
    }

    public void setValueprice(BigDecimal valueprice) {
        this.valueprice = valueprice;
    }

    public BigDecimal getVatValue() {
        return vatValue;
    }

    public void setVatValue(BigDecimal vatValue) {
        this.vatValue = vatValue;
    }

    public Vat getVatRate() {
        return vatRate;
    }

    public void setVatRate(Vat vatRate) {
        this.vatRate = vatRate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
