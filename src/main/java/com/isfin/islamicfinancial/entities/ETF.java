package com.isfin.islamicfinancial.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "etfs")
public class ETF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String symbol;
    private String provider;
    private double marketCap;

    @OneToOne(mappedBy = "etf", cascade = CascadeType.ALL, orphanRemoval = true)
    private FinancialRatios financialRatios;

    @OneToMany(mappedBy = "etf", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShariahCompliance> complianceRecords;

    // Default constructor
    public ETF() {}

    // Constructor without collections
    public ETF(String name, String symbol, String provider, double marketCap) {
        this.name = name;
        this.symbol = symbol;
        this.provider = provider;
        this.marketCap = marketCap;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getProvider() {
        return provider;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public FinancialRatios getFinancialRatios() {
        return financialRatios;
    }

    public List<ShariahCompliance> getComplianceRecords() {
        return complianceRecords;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public void setFinancialRatios(FinancialRatios financialRatios) {
        this.financialRatios = financialRatios;
    }

    public void setComplianceRecords(List<ShariahCompliance> complianceRecords) {
        this.complianceRecords = complianceRecords;
    }

    @Override
    public String toString() {
        return "ETF{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", provider='" + provider + '\'' +
                ", marketCap=" + marketCap +
                '}';
    }

}
