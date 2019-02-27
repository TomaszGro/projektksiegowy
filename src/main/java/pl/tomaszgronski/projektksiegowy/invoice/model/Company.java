package pl.tomaszgronski.projektksiegowy.invoice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Company implements Serializable {


    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "tax_indefication_number")
    private String taxIndetificationNumber;
    @Column(name = "adress")
    private String adress;
    @Column(name = "company_name")
    private String companyName;
    @OneToMany(mappedBy = "fromCompany")
    @JsonIgnore
    private List<Invoice> fromCompanyInvoices = new ArrayList<>();

    public List<Invoice> getFromCompanyInvoices() {
        return fromCompanyInvoices;
    }

    public void setFromCompanyInvoices(List<Invoice> fromCompanyInvoices) {
        this.fromCompanyInvoices = fromCompanyInvoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return taxIndetificationNumber.equals(company.taxIndetificationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxIndetificationNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaxIndetificationNumber() {
        return taxIndetificationNumber;
    }

    public void setTaxIndetificationNumber(String taxIndetificationNumber) {
        this.taxIndetificationNumber = taxIndetificationNumber;


    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Company() {
    }
}
