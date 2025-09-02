package com.isfin.islamicfinancial.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company_profiles")
public class CompanyProfile {

    @Id
    private String symbol;

    private Double price;
    private Double beta;
    private Long volAvg;
    private Long mktCap;
    private String companyName;
    private String industry;
    private String sector;

    // Add more fields as per API response if needed

    // Constructors
    public CompanyProfile() {}

    // Getters and setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getBeta() { return beta; }
    public void setBeta(Double beta) { this.beta = beta; }

    public Long getVolAvg() { return volAvg; }
    public void setVolAvg(Long volAvg) { this.volAvg = volAvg; }

    public Long getMktCap() { return mktCap; }
    public void setMktCap(Long mktCap) { this.mktCap = mktCap; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
}
