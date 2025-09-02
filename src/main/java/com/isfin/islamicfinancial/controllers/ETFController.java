package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.ETF;
import com.isfin.islamicfinancial.services.ETFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fmp/etf")
public class ETFController {

    private final ETFService etfService;

    @Autowired
    public ETFController(ETFService etfService) {
        this.etfService = etfService;
    }

    // Fetch live ETF data by symbol without saving to DB
    @GetMapping("/profile/{symbol}")
    public ResponseEntity<ETF> getETFProfile(@PathVariable String symbol) {
        try {
            ETF etf = etfService.fetchETFLive(symbol); // fetch live data only
            if (etf != null) {
                return ResponseEntity.ok(etf);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
