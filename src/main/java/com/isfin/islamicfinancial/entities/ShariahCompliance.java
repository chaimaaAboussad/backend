package com.isfin.islamicfinancial.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.isfin.islamicfinancial.models.ShariahStandard;

@Entity
@Table(name = "shariah_compliance")
public class ShariahCompliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the company this compliance record belongs to
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // Reference to the ETF this compliance record belongs to (optional)
    @ManyToOne
    @JoinColumn(name = "etf_id")
    private ETF etf;

    // Compliance status (true if compliant, false if not)
    @Column(name = "is_compliant", nullable = false)
    private boolean compliant;

    // Optional: date of compliance check
    @Column(name = "checked_date")
    private LocalDate checkedDate;

    // Optional: notes or details about compliance
    @Column(name = "notes", length = 1000)
    private String notes;

    // -----------------------------
    // New Fields
    // -----------------------------
    @Column(name = "standard", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ShariahStandard standard;

    @Column(name = "debt_ratio")
    private Double debtRatio;

    @Column(name = "cash_ratio")
    private Double cashRatio;

    @Column(name = "receivables_ratio")
    private Double receivablesRatio;

    @Column(name = "non_compliant_income_ratio")
    private Double nonCompliantIncomeRatio;

    // -----------------------------
    // Constructors
    // -----------------------------
    public ShariahCompliance() {}

    public ShariahCompliance(Company company, ETF etf, ShariahStandard standard,
                             boolean compliant, LocalDate checkedDate, String notes,
                             Double debtRatio, Double cashRatio, Double receivablesRatio,
                             Double nonCompliantIncomeRatio) {
        this.company = company;
        this.etf = etf;
        this.standard = standard;
        this.compliant = compliant;
        this.checkedDate = checkedDate;
        this.notes = notes;
        this.debtRatio = debtRatio;
        this.cashRatio = cashRatio;
        this.receivablesRatio = receivablesRatio;
        this.nonCompliantIncomeRatio = nonCompliantIncomeRatio;
    }

    // -----------------------------
    // Getters and Setters
    // -----------------------------
    public Long getId() { return id; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public ETF getEtf() { return etf; }
    public void setEtf(ETF etf) { this.etf = etf; }

    public boolean isCompliant() { return compliant; }
    public void setCompliant(boolean compliant) { this.compliant = compliant; }

    public LocalDate getCheckedDate() { return checkedDate; }
    public void setCheckedDate(LocalDate checkedDate) { this.checkedDate = checkedDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public ShariahStandard getStandard() { return standard; }
    public void setStandard(ShariahStandard standard) { this.standard = standard; }

    public Double getDebtRatio() { return debtRatio; }
    public void setDebtRatio(Double debtRatio) { this.debtRatio = debtRatio; }

    public Double getCashRatio() { return cashRatio; }
    public void setCashRatio(Double cashRatio) { this.cashRatio = cashRatio; }

    public Double getReceivablesRatio() { return receivablesRatio; }
    public void setReceivablesRatio(Double receivablesRatio) { this.receivablesRatio = receivablesRatio; }

    public Double getNonCompliantIncomeRatio() { return nonCompliantIncomeRatio; }
    public void setNonCompliantIncomeRatio(Double nonCompliantIncomeRatio) { this.nonCompliantIncomeRatio = nonCompliantIncomeRatio; }
}
