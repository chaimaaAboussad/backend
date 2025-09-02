package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FmpApiService {

    private final RestTemplate restTemplate;

    @Value("${fmp.api.key}")
    private String apiKey;

    @Value("${fmp.api.base-url}")
    private String baseUrl; // comes from application.properties

    public FmpApiService() {
        this.restTemplate = new RestTemplate();
    }

    // ---------------------------
    // 1️⃣ Fetch company profile
    // ---------------------------
    public Company getCompanyProfile(String symbol) {
        String url = baseUrl + "/profile/" + symbol + "?apikey=" + apiKey;
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

        if (response != null && !response.isEmpty()) {
            Map<String, Object> raw = response.get(0);

            Company company = new Company();
            company.setName((String) raw.get("companyName"));
            company.setSector((String) raw.get("sector"));
            company.setTicker((String) raw.get("symbol"));

            Object marketCapObj = raw.get("mktCap");
            if (marketCapObj instanceof Number) {
                company.setMarketCap(((Number) marketCapObj).doubleValue());
                company.setMktCap(((Number) marketCapObj).longValue());
            }

            Object priceObj = raw.get("price");
            if (priceObj instanceof Number) {
                company.setPrice(((Number) priceObj).doubleValue());
            }

            Object betaObj = raw.get("beta");
            if (betaObj instanceof Number) {
                company.setBeta(((Number) betaObj).doubleValue());
            }

            Object volAvgObj = raw.get("volAvg");
            if (volAvgObj instanceof Number) {
                company.setVolAvg(((Number) volAvgObj).longValue());
            }

            company.setIndustry((String) raw.get("industry"));
            company.setProvider("FMP");

            return company;
        }
        return null;
    }

    // ---------------------------
    // 2️⃣ Fetch financial ratios (key-metrics v5)
    // ---------------------------
    public Map<String, Object> getFinancialRatios(String symbol) {
        String url = baseUrl + "/key-metrics/" + symbol + "?apikey=" + apiKey;


        Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);
        Map<String, Object> ratios = new HashMap<>();

        if (response != null && response.length > 0) {
            Map<String, Object> data = response[0];
            ratios.put("debtRatio", data.getOrDefault("debtRatio", 0.0));
            ratios.put("cashRatio", data.getOrDefault("cashRatio", 0.0));
            ratios.put("receivablesRatio", data.getOrDefault("receivablesRatio", 0.0));
            ratios.put("nonCompliantIncome", data.getOrDefault("nonCompliantIncome", 0.0));
        }

        return ratios;
    }

    // ---------------------------
    // 3️⃣ Fetch latest stock price
    // ---------------------------
    public double getStockPrice(String symbol) {
        String url = baseUrl + "/quote-short/" + symbol + "?apikey=" + apiKey;

        Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);

        if (response != null && response.length > 0 && response[0].get("price") != null) {
            return Double.parseDouble(response[0].get("price").toString());
        }

        return 0.0;
    }

    // ---------------------------
    // 4️⃣ Fetch balance sheet (latest)
    // ---------------------------
    public Map<String, Object> getBalanceSheet(String symbol) {
        String url = baseUrl + "/balance-sheet-statement/" + symbol + "?limit=1&apikey=" + apiKey;

        Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);
        return response != null && response.length > 0 ? response[0] : new HashMap<>();
    }

    // ---------------------------
    // 5️⃣ Fetch income statement (latest)
    // ---------------------------
    public Map<String, Object> getIncomeStatement(String symbol) {
        String url = baseUrl + "/income-statement/" + symbol + "?limit=1&apikey=" + apiKey;

        Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);
        return response != null && response.length > 0 ? response[0] : new HashMap<>();
    }
}
