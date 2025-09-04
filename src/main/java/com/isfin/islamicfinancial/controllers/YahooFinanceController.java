package com.isfin.islamicfinancial.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.isfin.islamicfinancial.services.YahooFinanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yahoo")
public class YahooFinanceController {

    private final YahooFinanceService yahooFinanceService;

    public YahooFinanceController(YahooFinanceService yahooFinanceService) {
        this.yahooFinanceService = yahooFinanceService;
    }

    @GetMapping("/quote")
    public ResponseEntity<JsonNode> getQuote(@RequestParam String symbol) {
        return ResponseEntity.ok(yahooFinanceService.getQuote(symbol));
    }

    @GetMapping("/historical")
    public ResponseEntity<JsonNode> getHistoricalData(
            @RequestParam String symbol,
            @RequestParam(defaultValue = "6mo") String range,
            @RequestParam(defaultValue = "1d") String interval) {
        return ResponseEntity.ok(yahooFinanceService.getHistoricalData(symbol, range, interval));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchCompany(@RequestParam String q) {
        return ResponseEntity.ok(yahooFinanceService.searchCompany(q));
    }

    @GetMapping("/financials")
    public ResponseEntity<JsonNode> getFinancialData(@RequestParam String symbol) {
        return ResponseEntity.ok(yahooFinanceService.getFinancialData(symbol));
    }
}
