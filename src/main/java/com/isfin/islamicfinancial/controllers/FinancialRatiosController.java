package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.FinancialRatios;
import com.isfin.islamicfinancial.services.FinancialRatiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/financial-ratios")
public class FinancialRatiosController {

    private final FinancialRatiosService financialRatiosService;

    @Autowired
    public FinancialRatiosController(FinancialRatiosService financialRatiosService) {
        this.financialRatiosService = financialRatiosService;
    }

    // Get all financial ratios
    @GetMapping
    public ResponseEntity<List<FinancialRatios>> getAllFinancialRatios() {
        List<FinancialRatios> ratios = financialRatiosService.getAllFinancialRatios();
        return ResponseEntity.ok(ratios);
    }

    // Get financial ratios by ID
    @GetMapping("/{id}")
    public ResponseEntity<FinancialRatios> getFinancialRatiosById(@PathVariable Long id) {
        Optional<FinancialRatios> ratios = financialRatiosService.getFinancialRatiosById(id);
        return ratios.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new financial ratios record
    @PostMapping
    public ResponseEntity<FinancialRatios> createFinancialRatios(@RequestBody FinancialRatios financialRatios) {
        FinancialRatios savedRatios = financialRatiosService.saveFinancialRatios(financialRatios);
        return ResponseEntity.ok(savedRatios);
    }

    // Delete financial ratios by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancialRatios(@PathVariable Long id) {
        if (financialRatiosService.getFinancialRatiosById(id).isPresent()) {
            financialRatiosService.deleteFinancialRatios(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
