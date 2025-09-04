package com.isfin.islamicfinancial.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YahooFinanceService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rapidapi.key}")
    private String apiKey;

    @Value("${rapidapi.host:yahoo-finance15.p.rapidapi.com}")
    private String apiHost;

    public YahooFinanceService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);
        return new HttpEntity<>(headers);
    }

    // -------------------- Quote --------------------
    public JsonNode getQuote(String symbol) {
        String url = String.format("https://%s/market/v2/get-quotes?region=US&symbols=%s", apiHost, symbol);
        return fetchJson(url, "quote");
    }

    // -------------------- Historical --------------------
    public JsonNode getHistoricalData(String symbol, String range, String interval) {
        String url = String.format(
                "https://%s/stock/v3/get-historical-data?symbol=%s&region=US",
                apiHost, symbol
        );
        return fetchJson(url, "historical");
    }

    // -------------------- Search --------------------
    public JsonNode searchCompany(String query) {
        String url = String.format("https://%s/auto-complete?q=%s&region=US", apiHost, query);
        return fetchJson(url, "search");
    }

    // -------------------- Financials --------------------
    public JsonNode getFinancialData(String symbol) {
        String url = String.format(
                "https://%s/stock/v2/get-financials?symbol=%s&region=US",
                apiHost, symbol
        );
        return fetchJson(url, "financial data");
    }

    // -------------------- Helper Method --------------------
    private JsonNode fetchJson(String url, String type) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(), String.class);
            return objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch " + type + " JSON", e);
        }
    }
}
