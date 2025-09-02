package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.services.FinancialDataService;
import com.isfin.islamicfinancial.services.FinancialDataService.StandardBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/financial-data")
public class FinancialDataController {

    private final FinancialDataService financialDataService;

    @Autowired
    public FinancialDataController(FinancialDataService financialDataService) {
        this.financialDataService = financialDataService;
    }

    // Get all available Shariah standards
    @GetMapping("/standards")
    public ResponseEntity<List<String>> getAllStandards() {
        return ResponseEntity.ok(financialDataService.getAllStandards());
    }

    // Get compliance details for a company and standard
    @GetMapping("/compliance/{symbol}/{standard}")
    public ResponseEntity<Map<String, Object>> getCompanyCompliance(
            @PathVariable String symbol,
            @PathVariable String standard) {

        StandardBody body;
        try {
            body = StandardBody.valueOf(standard.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid standard name. Available: " + financialDataService.getAllStandards()));
        }

        Map<String, Object> result = financialDataService.getCompanyCompliance(symbol, body);
        return ResponseEntity.ok(result);
    }
}
