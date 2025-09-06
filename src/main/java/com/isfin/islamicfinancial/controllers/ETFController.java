package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.ETF;
import com.isfin.islamicfinancial.entities.CompanyProfile;
import com.isfin.islamicfinancial.services.ETFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/etfs")
public class ETFController {

    private final ETFService etfService;

    @Autowired
    public ETFController(ETFService etfService) {
        this.etfService = etfService;
    }

    @GetMapping
    public List<ETF> getAllETFs() {
        return etfService.getAllETFs();
    }

    @GetMapping("/{id}")
    public Optional<ETF> getETFById(@PathVariable Long id) {
        return etfService.getETFById(id);
    }

    @PostMapping
    public ETF saveETF(@RequestBody ETF etf) {
        return etfService.saveETF(etf);
    }

    @DeleteMapping("/{id}")
    public void deleteETF(@PathVariable Long id) {
        etfService.deleteETF(id);
    }

    // New endpoint to get live ETF/company profile
    @GetMapping("/live/{symbol}")
    public CompanyProfile getETFLive(@PathVariable String symbol) {
        return etfService.fetchETFLive(symbol);
    }
}
