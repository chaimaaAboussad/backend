package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.Company;
import com.isfin.islamicfinancial.entities.ShariahCompliance;
import com.isfin.islamicfinancial.models.ShariahStandard;
import com.isfin.islamicfinancial.repositories.ShariahComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShariahComplianceService {

    private final MarketDataService marketDataService;
    private final ShariahComplianceRepository repository;

    @Autowired
    public ShariahComplianceService(MarketDataService marketDataService,
                                    ShariahComplianceRepository repository) {
        this.marketDataService = marketDataService;
        this.repository = repository;
    }

    // --------------------
    // Compute compliance from MarketDataService
    // --------------------
    public ShariahCompliance computeCompliance(Company company, ShariahStandard standard) {
        ShariahCompliance existing = repository.findByCompanyAndStandard(company, standard).orElse(null);
        if (existing != null) return existing;

        // Fetch company profile from MarketDataService
        var profile = marketDataService.fetchCompanyProfile(company.getTicker());

        double marketCap = profile.getMktCap() != null ? profile.getMktCap() : 0.0;
        double totalDebt = profile.getTotalDebt() != null ? profile.getTotalDebt() : 0.0;
        double cash = profile.getCash() != null ? profile.getCash() : 0.0;
        double shortTermInvestments = profile.getShortTermInvestments() != null ? profile.getShortTermInvestments() : 0.0;
        double accountsReceivable = profile.getAccountsReceivable() != null ? profile.getAccountsReceivable() : 0.0;
        double interestIncome = profile.getInterestIncome() != null ? profile.getInterestIncome() : 0.0;
        double revenue = profile.getTotalRevenue() != null ? profile.getTotalRevenue() : 0.0;

        ShariahCompliance compliance = new ShariahCompliance();
        compliance.setCompany(company);
        compliance.setStandard(standard);

        // Compute ratios
        compliance.setDebtRatio(safeDiv(totalDebt, marketCap));
        compliance.setCashRatio(safeDiv(cash + shortTermInvestments, marketCap));
        compliance.setReceivablesRatio(safeDiv(accountsReceivable, marketCap));
        compliance.setNonCompliantIncomeRatio(safeDiv(interestIncome, revenue));

        return repository.save(compliance);
    }

    // --------------------
    // Fetch compliance from DB or compute
    // --------------------
    public ShariahCompliance getCompliance(Company company, ShariahStandard standard) {
        return repository.findByCompanyAndStandard(company, standard)
                .orElseGet(() -> computeCompliance(company, standard));
    }

    // --------------------
    // CRUD methods
    // --------------------
    public List<ShariahCompliance> getAllComplianceRecords() {
        return repository.findAll();
    }

    public Optional<ShariahCompliance> getComplianceById(Long id) {
        return repository.findById(id);
    }

    public ShariahCompliance saveComplianceRecord(ShariahCompliance compliance) {
        return repository.save(compliance);
    }

    public void deleteComplianceRecord(Long id) {
        repository.deleteById(id);
    }

    public List<ShariahCompliance> getComplianceRecordsByCompanyId(Long companyId) {
        return repository.findAllByCompanyId(companyId);
    }

    public boolean isCompanyCompliant(Long companyId) {
        List<ShariahCompliance> records = repository.findAllByCompanyId(companyId);
        return records.stream().allMatch(r -> r.getNonCompliantIncomeRatio() <= 0.05);
    }

    public ShariahCompliance checkCompliance(String symbol, ShariahStandard standard) {
        Company company = new Company();
        company.setTicker(symbol);
        return computeCompliance(company, standard);
    }

    // --------------------
    // Helper
    // --------------------
    private double safeDiv(double a, double b) {
        return b == 0 ? 0.0 : a / b;
    }
}
