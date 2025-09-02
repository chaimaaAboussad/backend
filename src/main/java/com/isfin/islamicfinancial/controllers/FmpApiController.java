package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.Company;
import com.isfin.islamicfinancial.services.FmpApiService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/fmp")
public class FmpApiController {

    private final FmpApiService fmpApiService;

    public FmpApiController(FmpApiService fmpApiService) {
        this.fmpApiService = fmpApiService;
    }

    // 1️⃣ Get company profile
    @GetMapping("/profile/{symbol}")
    public Company getProfile(@PathVariable String symbol) {
        return fmpApiService.getCompanyProfile(symbol);
    }

    // 2️⃣ Get financial ratios (key-metrics v5)
    @GetMapping("/key-metrics/{symbol}")
    public Map<String, Object> getKeyMetrics(@PathVariable String symbol) {
        return fmpApiService.getFinancialRatios(symbol);
    }

    // 3️⃣ Get stock price
    @GetMapping("/price/{symbol}")
    public double getPrice(@PathVariable String symbol) {
        return fmpApiService.getStockPrice(symbol);
    }

    // 4️⃣ Get latest balance sheet
    @GetMapping("/balance-sheet/{symbol}")
    public Map<String, Object> getBalanceSheet(@PathVariable String symbol) {
        return fmpApiService.getBalanceSheet(symbol);
    }

    // 5️⃣ Get latest income statement
    @GetMapping("/income-statement/{symbol}")
    public Map<String, Object> getIncomeStatement(@PathVariable String symbol) {
        return fmpApiService.getIncomeStatement(symbol);
    }
}
