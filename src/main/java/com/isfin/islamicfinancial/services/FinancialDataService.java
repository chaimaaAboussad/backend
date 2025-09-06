package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.CompanyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinancialDataService {

    public enum StandardBody { AAOIFI, DJIM, FTSE, MSCI, SP, STOXX, KLSE, ALRAJHI }

    @Autowired
    private MarketDataService marketDataService;

    // ------------------------
    // Threshold configuration
    // ------------------------
    static class ThresholdConfig {
        public double debtThreshold;
        public double cashAndInterestThreshold;
        public double receivablesThreshold;
        public double impermissibleIncomeThreshold;
        public Denominator denomForDebt;
        public int marketCapAverageMonths;
    }

    enum Denominator { TOTAL_ASSETS, MARKET_CAP_AVG }

    private final Map<StandardBody, ThresholdConfig> configs = initConfigs();

    private Map<StandardBody, ThresholdConfig> initConfigs() {
        Map<StandardBody, ThresholdConfig> m = new EnumMap<>(StandardBody.class);

        // Example: AAOIFI thresholds
        ThresholdConfig aaoifi = new ThresholdConfig();
        aaoifi.debtThreshold = 0.30;
        aaoifi.cashAndInterestThreshold = 0.30;
        aaoifi.receivablesThreshold = 0.33;
        aaoifi.impermissibleIncomeThreshold = 0.05;
        aaoifi.denomForDebt = Denominator.MARKET_CAP_AVG;
        aaoifi.marketCapAverageMonths = 36;
        m.put(StandardBody.AAOIFI, aaoifi);

        // TODO: Add other standards (DJIM, FTSE, MSCI, SP, STOXX, KLSE, ALRAJHI)
        return m;
    }

    // ------------------------
    // Main compliance calculation
    // ------------------------
    public Map<String,Object> getCompanyCompliance(String symbol, StandardBody body) {
        Map<String,Object> out = new HashMap<>();

        // Fetch full company profile from MarketDataService
        CompanyProfile profile = marketDataService.fetchCompanyProfile(symbol);

        ThresholdConfig cfg = configs.get(body);

        // Determine debt denominator
        double debtDenom = 0.0;
        if (cfg.denomForDebt == Denominator.TOTAL_ASSETS) {
            debtDenom = profile.getTotalAssets() != null ? profile.getTotalAssets() : 0.0;
        } else {
            debtDenom = profile.getMktCap() != null ? profile.getMktCap() : 0.0;
        }

        // Financial ratios
        double totalDebt = profile.getTotalDebt() != null ? profile.getTotalDebt() : 0.0;
        double cashAndShortInvest =
                (profile.getCash() != null ? profile.getCash() : 0.0) +
                        (profile.getShortTermInvestments() != null ? profile.getShortTermInvestments() : 0.0);
        double accountsReceivable = profile.getAccountsReceivable() != null ? profile.getAccountsReceivable() : 0.0;
        double interestIncome = profile.getInterestIncome() != null ? profile.getInterestIncome() : 0.0;
        double revenue = profile.getTotalRevenue() != null ? profile.getTotalRevenue() : 0.0;

        double debtRatio = safeDiv(totalDebt, debtDenom);
        double cashAndInterestRatio = safeDiv(cashAndShortInvest, debtDenom);
        double receivablesRatio = safeDiv(accountsReceivable, debtDenom);
        double impermissibleIncomeRatio = safeDiv(interestIncome, revenue);

        // Map each ratio with threshold pass/fail
        Map<String,Object> details = new LinkedHashMap<>();
        details.put("debtRatio", mapVal(debtRatio, cfg.debtThreshold));
        details.put("cashAndInterestRatio", mapVal(cashAndInterestRatio, cfg.cashAndInterestThreshold));
        details.put("receivablesRatio", mapVal(receivablesRatio, cfg.receivablesThreshold));
        details.put("impermissibleIncomeRatio", mapVal(impermissibleIncomeRatio, cfg.impermissibleIncomeThreshold));

        boolean overallPass =
                ((boolean)((Map)details.get("debtRatio")).get("pass")) &&
                        ((boolean)((Map)details.get("cashAndInterestRatio")).get("pass")) &&
                        ((boolean)((Map)details.get("receivablesRatio")).get("pass")) &&
                        ((boolean)((Map)details.get("impermissibleIncomeRatio")).get("pass"));

        // Output
        Map<String,Object> profileMap = new HashMap<>();
        profileMap.put("symbol", profile.getSymbol());
        profileMap.put("companyName", profile.getCompanyName());
        profileMap.put("industry", profile.getIndustry());
        profileMap.put("sector", profile.getSector());
        profileMap.put("marketCap", profile.getMktCap());
        profileMap.put("price", profile.getPrice());

        out.put("profile", profileMap);
        out.put("details", details);
        out.put("standard", body.name());
        out.put("overall", overallPass ? "✅ Halal" : "❌ Not Halal");

        return out;
    }

    // ------------------------
    // Helper methods
    // ------------------------
    private Map<String,Object> mapVal(double val, double threshold) {
        Map<String,Object> m = new HashMap<>();
        m.put("value", val);
        m.put("threshold", threshold);
        m.put("pass", val <= threshold);
        return m;
    }

    private double safeDiv(double a, double b) { return b==0 ? 0.0 : a / b; }

    public List<String> getAllStandards() {
        List<String> list = new ArrayList<>();
        for (StandardBody b : StandardBody.values()) list.add(b.name());
        return list;
    }
}
