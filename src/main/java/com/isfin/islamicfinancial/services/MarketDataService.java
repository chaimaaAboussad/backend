package com.isfin.islamicfinancial.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isfin.islamicfinancial.entities.CompanyProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketDataService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${alpha.api.key}")
    private String alphaApiKey;
    @Value("${alpha.api.base-url}")
    private String alphaBaseUrl;

    @Value("${polygon.api.key}")
    private String polygonApiKey;
    @Value("${polygon.api.base-url}")
    private String polygonBaseUrl;

    // ------------------------
    // Fetch combined company profile
    // ------------------------
    public CompanyProfile fetchCompanyProfile(String ticker) {
        CompanyProfile profile = new CompanyProfile();
        profile.setSymbol(ticker);

        // ------------------------
        // Alpha Vantage API
        // ------------------------
        try {
            // Global Quote
            String quoteUrl = alphaBaseUrl + "/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + alphaApiKey;
            Map<String, Object> quoteResp = restTemplate.getForObject(quoteUrl, HashMap.class);
            if (quoteResp != null && quoteResp.containsKey("Global Quote")) {
                Map<String, String> quote = (Map<String, String>) quoteResp.get("Global Quote");
                profile.setPrice(toDouble(quote.get("05. price")));
            } else {
                System.out.println("Alpha Global Quote missing for " + ticker);
                profile.setPrice(null);
            }

            // Income Statement
            String incomeUrl = alphaBaseUrl + "/query?function=INCOME_STATEMENT&symbol=" + ticker + "&apikey=" + alphaApiKey;
            Map<String, Object> incomeResp = restTemplate.getForObject(incomeUrl, HashMap.class);
            if (incomeResp != null && incomeResp.containsKey("annualReports")) {
                List<Map<String, String>> reports = (List<Map<String, String>>) incomeResp.get("annualReports");
                if (!reports.isEmpty()) {
                    Map<String, String> latest = reports.get(0);
                    profile.setTotalRevenue(toDouble(latest.get("totalRevenue")));
                    profile.setInterestIncome(toDouble(latest.get("interestIncome")));
                }
            } else {
                System.out.println("Alpha Income Statement missing for " + ticker);
            }

            // Balance Sheet
            String balanceUrl = alphaBaseUrl + "/query?function=BALANCE_SHEET&symbol=" + ticker + "&apikey=" + alphaApiKey;
            Map<String, Object> balanceResp = restTemplate.getForObject(balanceUrl, HashMap.class);
            if (balanceResp != null && balanceResp.containsKey("annualReports")) {
                List<Map<String, String>> reports = (List<Map<String, String>>) balanceResp.get("annualReports");
                if (!reports.isEmpty()) {
                    Map<String, String> latest = reports.get(0);
                    profile.setTotalDebt(toDouble(latest.get("shortLongTermDebtTotal")));
                    profile.setSharesOutstanding(toDouble(latest.get("commonStockSharesOutstanding")));
                    profile.setCash(toDouble(latest.get("cashAndCashEquivalents")));
                    profile.setShortTermInvestments(toDouble(latest.get("shortTermInvestments")));
                    profile.setAccountsReceivable(toDouble(latest.get("accountsReceivable")));
                    profile.setTotalAssets(toDouble(latest.get("totalAssets")));
                }
            } else {
                System.out.println("Alpha Balance Sheet missing for " + ticker);
            }

        } catch (Exception e) {
            System.out.println("Alpha Vantage error for " + ticker + ": " + e.getMessage());
        }

        // ------------------------
        // Polygon.io API
        // ------------------------
        try {
            String url = polygonBaseUrl + "/v3/reference/tickers/" + ticker + "?apiKey=" + polygonApiKey;
            Map<String, Object> resp = restTemplate.getForObject(url, HashMap.class);

            if (resp != null && resp.containsKey("results")) {
                Map results = (Map) resp.get("results");
                if (results != null) {
                    profile.setMktCap(toLong(results.get("market_cap")));
                    profile.setCompanyName((String) results.get("name"));
                    profile.setIndustry((String) results.get("sic_description"));
                    profile.setSector((String) results.get("market"));
                    profile.setDescription((String) results.get("description"));
                    profile.setEmployees(toLong(results.get("total_employees")));
                    profile.setWebsite((String) results.get("homepage_url"));

                    Map branding = (Map) results.get("branding");
                    if (branding != null) {
                        profile.setLogoUrl((String) branding.get("logo_url"));
                        profile.setIconUrl((String) branding.get("icon_url"));
                    }
                } else {
                    System.out.println("Polygon results null for " + ticker);
                }
            } else {
                System.out.println("Polygon API returned no results for " + ticker);
            }
        } catch (Exception e) {
            System.out.println("Polygon.io error for " + ticker + ": " + e.getMessage());
        }

        return profile;
    }

    // ------------------------
    // Return combined JSON for Flutter/Postman
    // ------------------------
    public JsonNode getCombinedData(String symbol) {
        ObjectNode combined = objectMapper.createObjectNode();
        try {
            CompanyProfile profile = fetchCompanyProfile(symbol);
            combined.set("profile", objectMapper.valueToTree(profile));
        } catch (Exception e) {
            combined.put("error", "Failed to fetch data for " + symbol + ": " + e.getMessage());
            e.printStackTrace();
        }
        return combined;
    }

    // ------------------------
    // Helpers
    // ------------------------
    private Double toDouble(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        try {
            return Double.valueOf(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private Long toLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).longValue();
        try {
            return Long.valueOf(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
