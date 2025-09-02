package com.isfin.islamicfinancial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinancialDataService {

    public enum StandardBody { AAOIFI, DJIM, FTSE, MSCI, SP, STOXX, KLSE, ALRAJHI }

    @Autowired
    private FmpApiService fmpApiService;

    // Threshold config
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

        // AAOIFI
        ThresholdConfig aaoifi = new ThresholdConfig();
        aaoifi.debtThreshold = 0.30;
        aaoifi.cashAndInterestThreshold = 0.30;
        aaoifi.receivablesThreshold = 0.33;
        aaoifi.impermissibleIncomeThreshold = 0.05;
        aaoifi.denomForDebt = Denominator.MARKET_CAP_AVG;
        aaoifi.marketCapAverageMonths = 36;
        m.put(StandardBody.AAOIFI, aaoifi);

        // Other standards (DJIM, FTSE, MSCI, SP, STOXX, KLSE, ALRAJHI) ...
        // Copy previous thresholds here as in your current code

        return m;
    }

    // Main compliance method
    public Map<String,Object> getCompanyCompliance(String symbol, StandardBody body) {
        Map<String,Object> out = new HashMap<>();

        Map<String,Object> profile = fmpApiService.getCompanyProfile(symbol) != null ?
                Map.of(
                        "companyName", fmpApiService.getCompanyProfile(symbol).getName(),
                        "symbol", fmpApiService.getCompanyProfile(symbol).getTicker(),
                        "industry", fmpApiService.getCompanyProfile(symbol).getIndustry(),
                        "sector", fmpApiService.getCompanyProfile(symbol).getSector(),
                        "marketCap", fmpApiService.getCompanyProfile(symbol).getMarketCap(),
                        "price", fmpApiService.getCompanyProfile(symbol).getPrice()
                ) : new HashMap<>();

        Map<String,Object> bs = fmpApiService.getBalanceSheet(symbol);
        Map<String,Object> isheet = fmpApiService.getIncomeStatement(symbol);
        Map<String,Object> ratios = fmpApiService.getFinancialRatios(symbol);

        ThresholdConfig cfg = configs.get(body);
        Double denominatorMarketCapAvg = null;
        if (cfg.denomForDebt == Denominator.MARKET_CAP_AVG && cfg.marketCapAverageMonths > 0) {
            denominatorMarketCapAvg = profile.get("marketCap") != null ?
                    Double.parseDouble(profile.get("marketCap").toString()) : null;
        }

        double totalDebt = getDouble(bs, "totalDebt");
        double totalAssets = getDouble(bs, "totalAssets");
        double cash = getDouble(bs, "cash");
        double shortTermInvest = getDouble(bs, "shortTermInvestments");
        double accountsReceivable = getDouble(bs, "accountsReceivable");
        double marketCap = profile.get("marketCap") != null ? Double.parseDouble(profile.get("marketCap").toString()) : 0.0;
        double revenue = getDouble(isheet, "revenue");
        double interestIncome = getDouble(isheet, "interestIncome");

        double debtDenom = (cfg.denomForDebt == Denominator.TOTAL_ASSETS) ? totalAssets
                : (denominatorMarketCapAvg != null ? denominatorMarketCapAvg : marketCap);

        double debtRatio = safeDiv(totalDebt, debtDenom);
        double cashAndInterestRatio = safeDiv(cash + shortTermInvest, debtDenom);
        double receivablesRatio = safeDiv(accountsReceivable, debtDenom);
        double impermissibleIncomeRatio = safeDiv(interestIncome, revenue);

        Map<String,Object> details = new LinkedHashMap<>();
        details.put("debtRatio", mapVal(debtRatio, cfg.debtThreshold));
        details.put("cashAndInterestRatio", mapVal(cashAndInterestRatio, cfg.cashAndInterestThreshold));
        details.put("receivablesRatio", mapVal(receivablesRatio, cfg.receivablesThreshold));
        details.put("impermissibleIncomeRatio", mapVal(impermissibleIncomeRatio, cfg.impermissibleIncomeThreshold));

        boolean overallPass = ((boolean)((Map)details.get("debtRatio")).get("pass"))
                && ((boolean)((Map)details.get("cashAndInterestRatio")).get("pass"))
                && ((boolean)((Map)details.get("receivablesRatio")).get("pass"))
                && ((boolean)((Map)details.get("impermissibleIncomeRatio")).get("pass"));

        out.put("profile", profile);
        out.put("details", details);
        out.put("standard", body.name());
        out.put("overall", overallPass ? "✅ Halal" : "❌ Not Halal");

        return out;
    }

    private Map<String,Object> mapVal(double val, double threshold) {
        Map<String,Object> m = new HashMap<>();
        m.put("value", val);
        m.put("threshold", threshold);
        m.put("pass", val <= threshold);
        return m;
    }

    private double getDouble(Map<String,Object> m, String key) {
        try {
            if (m==null) return 0.0;
            Object o = m.get(key);
            return o == null ? 0.0 : Double.parseDouble(o.toString());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private double safeDiv(double a, double b) { return b==0 ? 0.0 : a / b; }

    public List<String> getAllStandards() {
        List<String> list = new ArrayList<>();
        for (StandardBody b : StandardBody.values()) list.add(b.name());
        return list;
    }
}
