package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.ShariahCompliance;
import com.isfin.islamicfinancial.models.ShariahStandard;
import com.isfin.islamicfinancial.services.ShariahComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shariah-compliance")
public class ShariahComplianceController {

    private final ShariahComplianceService shariahComplianceService;

    @Autowired
    public ShariahComplianceController(ShariahComplianceService shariahComplianceService) {
        this.shariahComplianceService = shariahComplianceService;
    }

    // Get all compliance records
    @GetMapping
    public ResponseEntity<List<ShariahCompliance>> getAllComplianceRecords() {
        List<ShariahCompliance> records = shariahComplianceService.getAllComplianceRecords();
        return ResponseEntity.ok(records);
    }

    // Get compliance record by ID
    @GetMapping("/{id}")
    public ResponseEntity<ShariahCompliance> getComplianceById(@PathVariable Long id) {
        Optional<ShariahCompliance> record = shariahComplianceService.getComplianceById(id);
        return record.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new compliance record
    @PostMapping
    public ResponseEntity<ShariahCompliance> createComplianceRecord(@RequestBody ShariahCompliance record) {
        ShariahCompliance savedRecord = shariahComplianceService.saveComplianceRecord(record);
        return ResponseEntity.ok(savedRecord);
    }

    // Delete compliance record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplianceRecord(@PathVariable Long id) {
        if (shariahComplianceService.getComplianceById(id).isPresent()) {
            shariahComplianceService.deleteComplianceRecord(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all compliance records for a specific company (by ID)
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ShariahCompliance>> getComplianceByCompanyId(@PathVariable Long companyId) {
        List<ShariahCompliance> records = shariahComplianceService.getComplianceRecordsByCompanyId(companyId);
        return ResponseEntity.ok(records);
    }

    // Check if a company is compliant (boolean) by ID
    @GetMapping("/check/{companyId}")
    public ResponseEntity<Boolean> checkComplianceByCompanyId(@PathVariable Long companyId) {
        boolean compliant = shariahComplianceService.isCompanyCompliant(companyId);
        return ResponseEntity.ok(compliant);
    }

    // ----------------------------
    // Check compliance by symbol & standard
    // ----------------------------
    @GetMapping("/check")
    public ResponseEntity<ShariahCompliance> checkComplianceBySymbol(
            @RequestParam String symbol,
            @RequestParam String standard // e.g., "AAOIFI", "DJIM", "FTSE"
    ) {
        try {
            ShariahStandard shariahStandard = ShariahStandard.valueOf(standard.toUpperCase());
            ShariahCompliance compliance = shariahComplianceService.checkCompliance(symbol, shariahStandard);
            return ResponseEntity.ok(compliance);
        } catch (IllegalArgumentException e) {
            // Invalid standard provided
            return ResponseEntity.badRequest().body(null);
        }
    }
}
