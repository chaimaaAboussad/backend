package com.isfin.islamicfinancial.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_screeners")
public class StockScreener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockSymbol;
    private String companyName;
    private Double marketCap;
    private Double price;
    private Boolean shariahCompliant;

    // Constructors
    public StockScreener() {}

    public StockScreener(String stockSymbol, String companyName, Double marketCap, Double price, Boolean shariahCompliant) {
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.marketCap = marketCap;
        this.price = price;
        this.shariahCompliant = shariahCompliant;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getShariahCompliant() {
        return shariahCompliant;
    }

    public void setShariahCompliant(Boolean shariahCompliant) {
        this.shariahCompliant = shariahCompliant;
    }
}
