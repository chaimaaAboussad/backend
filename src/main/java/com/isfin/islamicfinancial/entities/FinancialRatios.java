package com.isfin.islamicfinancial.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "financial_ratios")
public class FinancialRatios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double debtRatio;
    private double interestBearingAssetRatio;
    private double liquidityRatio;
    private double impermissibleIncomeRatio;

    @OneToOne
    @JoinColumn(name = "company_id", unique = true)
    private Company company;

    @OneToOne
    @JoinColumn(name = "etf_id", unique = true)
    private ETF etf;

    // Default constructor
    public FinancialRatios() {}

    // Constructor with all fields including company and etf
    public FinancialRatios(double debtRatio, double interestBearingAssetRatio, double liquidityRatio,
                           double impermissibleIncomeRatio, Company company, ETF etf) {
        this.debtRatio = debtRatio;
        this.interestBearingAssetRatio = interestBearingAssetRatio;
        this.liquidityRatio = liquidityRatio;
        this.impermissibleIncomeRatio = impermissibleIncomeRatio;
        this.company = company;
        this.etf = etf;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public double getDebtRatio() {
        return debtRatio;
    }

    public void setDebtRatio(double debtRatio) {
        this.debtRatio = debtRatio;
    }

    public double getInterestBearingAssetRatio() {
        return interestBearingAssetRatio;
    }

    public void setInterestBearingAssetRatio(double interestBearingAssetRatio) {
        this.interestBearingAssetRatio = interestBearingAssetRatio;
    }

    public double getLiquidityRatio() {
        return liquidityRatio;
    }

    public void setLiquidityRatio(double liquidityRatio) {
        this.liquidityRatio = liquidityRatio;
    }

    public double getImpermissibleIncomeRatio() {
        return impermissibleIncomeRatio;
    }

    public void setImpermissibleIncomeRatio(double impermissibleIncomeRatio) {
        this.impermissibleIncomeRatio = impermissibleIncomeRatio;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ETF getEtf() {
        return etf;
    }

    public void setEtf(ETF etf) {
        this.etf = etf;
    }
}
