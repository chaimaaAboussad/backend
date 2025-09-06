package com.isfin.islamicfinancial.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "company_profiles")
public class CompanyProfile {

    @Id
    private String symbol;

    // Basic info
    private String companyName;
    private String industry;
    private String sector;
    private String description;
    private Long employees;
    private String website;
    private String logoUrl;
    private String iconUrl;

    // Market data
    private Double price;
    private Long mktCap;
    private Double totalAssets;
    private Double enterpriseValue;
    private Double sharesOutstanding;

    // Financials for Shariah compliance
    private Double totalDebt;
    private Double totalRevenue;
    private Double cash;
    private Double shortTermInvestments;
    private Double accountsReceivable;
    private Double interestIncome;

    // --------------------
    // Getters & Setters
    // --------------------

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getEmployees() { return employees; }
    public void setEmployees(Long employees) { this.employees = employees; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Long getMktCap() { return mktCap; }
    public void setMktCap(Long mktCap) { this.mktCap = mktCap; }

    public Double getTotalAssets() { return totalAssets; }
    public void setTotalAssets(Double totalAssets) { this.totalAssets = totalAssets; }

    public Double getEnterpriseValue() { return enterpriseValue; }
    public void setEnterpriseValue(Double enterpriseValue) { this.enterpriseValue = enterpriseValue; }

    public Double getSharesOutstanding() { return sharesOutstanding; }
    public void setSharesOutstanding(Double sharesOutstanding) { this.sharesOutstanding = sharesOutstanding; }

    public Double getTotalDebt() { return totalDebt; }
    public void setTotalDebt(Double totalDebt) { this.totalDebt = totalDebt; }

    public Double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }

    public Double getCash() { return cash; }
    public void setCash(Double cash) { this.cash = cash; }

    public Double getShortTermInvestments() { return shortTermInvestments; }
    public void setShortTermInvestments(Double shortTermInvestments) { this.shortTermInvestments = shortTermInvestments; }

    public Double getAccountsReceivable() { return accountsReceivable; }
    public void setAccountsReceivable(Double accountsReceivable) { this.accountsReceivable = accountsReceivable; }

    public Double getInterestIncome() { return interestIncome; }
    public void setInterestIncome(Double interestIncome) { this.interestIncome = interestIncome; }
}
