package com.isfin.islamicfinancial.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isfin.islamicfinancial.entities.CompanyProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarketDataService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${zoya.api.key}")
    private String zoyaApiKey;
    @Value("${zoya.api.base-url}")
    private String zoyaBaseUrl;

    @Value("${alpha.api.key}")
    private String alphaApiKey;
    @Value("${alpha.api.base-url}")
    private String alphaBaseUrl;

    @Value("${polygon.api.key}")
    private String polygonApiKey;
    @Value("${polygon.api.base-url}")
    private String polygonBaseUrl;

    // ------------------------
    // Fetch combined profile from all APIs
    // ------------------------
    public CompanyProfile fetchCompanyProfile(String ticker) {
        CompanyProfile profile = new CompanyProfile();
        profile.setSymbol(ticker);

        // 1️⃣ Zoya API - financial data
        try {
            String url = zoyaBaseUrl + "/companies/" + ticker + "/financials?apikey=" + zoyaApiKey;
            ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
            Map data = resp.getBody();
            if (data != null) {
                Map financialData = (Map) data.get("financialData");
                if (financialData != null) {
                    profile.setTotalDebt(toDouble(financialData.get("totalDebt")));
                    profile.setTotalRevenue(toDouble(financialData.get("totalRevenue")));
                }
                Map keyStats = (Map) data.get("defaultKeyStatistics");
                if (keyStats != null) {
                    profile.setEnterpriseValue(toDouble(keyStats.get("enterpriseValue")));
                    profile.setSharesOutstanding(toDouble(keyStats.get("sharesOutstanding")));
                }
            }
        } catch (Exception e) {
            System.out.println("Zoya API error: " + e.getMessage());
        }

        // 2️⃣ Alpha Vantage API - stock price
        try {
            String url = alphaBaseUrl + "/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + alphaApiKey;
            Map<String, Object> resp = restTemplate.getForObject(url, HashMap.class);
            if (resp != null && resp.containsKey("Global Quote")) {
                Map<String, String> quote = (Map<String, String>) resp.get("Global Quote");
                profile.setPrice(Double.valueOf(quote.get("05. price")));
            }
        } catch (Exception e) {
            System.out.println("Alpha Vantage error: " + e.getMessage());
        }

        // 3️⃣ Polygon.io API - market cap & industry/sector
        try {
            String url = polygonBaseUrl + "/v3/reference/tickers/" + ticker + "?apiKey=" + polygonApiKey;

            Map<String, Object> resp = restTemplate.getForObject(url, HashMap.class);
            if (resp != null && resp.containsKey("results")) {
                Map results = (Map) resp.get("results");
                profile.setMktCap(toLong(results.get("market_cap")));
                profile.setIndustry((String) results.get("industry"));
                profile.setSector((String) results.get("sector"));
            }
        } catch (Exception e) {
            System.out.println("Polygon.io error: " + e.getMessage());
        }

        return profile;
    }

    // ------------------------
    // Return combined JSON for Flutter
    // ------------------------
    public JsonNode getCombinedData(String symbol) {
        ObjectNode combined = objectMapper.createObjectNode();
        try {
            CompanyProfile profile = fetchCompanyProfile(symbol);
            combined.set("profile", objectMapper.valueToTree(profile));
        } catch (Exception e) {
            combined.put("error", "Failed to fetch data: " + e.getMessage());
        }
        return combined;
    }

    // ------------------------
    // Helpers
    // ------------------------
    private Double toDouble(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).doubleValue();
        return Double.valueOf(obj.toString());
    }

    private Long toLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).longValue();
        return Long.valueOf(obj.toString());
    }
}
