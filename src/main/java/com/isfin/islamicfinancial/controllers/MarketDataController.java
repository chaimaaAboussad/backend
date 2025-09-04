package com.isfin.islamicfinancial.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.isfin.islamicfinancial.services.MarketDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/combined")
    public ResponseEntity<JsonNode> getCombinedData(@RequestParam String symbol) {
        return ResponseEntity.ok(marketDataService.getCombinedData(symbol));
    }
}
