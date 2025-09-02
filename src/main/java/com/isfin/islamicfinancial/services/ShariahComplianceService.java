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

    private final FmpApiService fmpApiService;
    private final ShariahComplianceRepository repository;

    @Autowired
    public ShariahComplianceService(FmpApiService fmpApiService,
                                    ShariahComplianceRepository repository) {
        this.fmpApiService = fmpApiService;
        this.repository = repository;
    }

    // --------------------
    // Existing methods
    // --------------------
    public ShariahCompliance computeCompliance(Company company, ShariahStandard standard) {
        ShariahCompliance existing = repository.findByCompanyAndStandard(company, standard).orElse(null);
        if (existing != null) return existing;

        var ratios = fmpApiService.getFinancialRatios(company.getTicker());
        double debt = ((Number) ratios.getOrDefault("debtRatio", 0.0)).doubleValue();
        double cash = ((Number) ratios.getOrDefault("cashRatio", 0.0)).doubleValue();
        double receivables = ((Number) ratios.getOrDefault("receivablesRatio", 0.0)).doubleValue();
        double nonCompliantIncome = ((Number) ratios.getOrDefault("nonCompliantIncome", 0.0)).doubleValue();

        ShariahCompliance compliance = new ShariahCompliance();
        compliance.setCompany(company);
        compliance.setStandard(standard);
        compliance.setDebtRatio(debt);
        compliance.setCashRatio(cash);
        compliance.setReceivablesRatio(receivables);
        compliance.setNonCompliantIncomeRatio(nonCompliantIncome);

        return repository.save(compliance);
    }

    public ShariahCompliance getCompliance(Company company, ShariahStandard standard) {
        return repository.findByCompanyAndStandard(company, standard)
                .orElseGet(() -> computeCompliance(company, standard));
    }

    // --------------------
    // CRUD methods for controller
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
        // Example: check all standards for the company
        List<ShariahCompliance> records = repository.findAllByCompanyId(companyId);
        return records.stream().allMatch(r -> r.getNonCompliantIncomeRatio() <= 5.0);
    }

    public ShariahCompliance checkCompliance(String symbol, ShariahStandard standard) {
        // Fetch company from DB first (implement getCompanyBySymbol)
        Company company = new Company();
        company.setTicker(symbol);
        return computeCompliance(company, standard);
    }
}
