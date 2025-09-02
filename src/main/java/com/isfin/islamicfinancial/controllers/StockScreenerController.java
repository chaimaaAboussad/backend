package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.StockScreener;
import com.isfin.islamicfinancial.services.StockScreenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock-screener")
public class StockScreenerController {

    private final StockScreenerService stockScreenerService;

    @Autowired
    public StockScreenerController(StockScreenerService stockScreenerService) {
        this.stockScreenerService = stockScreenerService;
    }

    @GetMapping
    public ResponseEntity<List<StockScreener>> getAllStocks() {
        List<StockScreener> stocks = stockScreenerService.getAllStockScreeners();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockScreener> getStockById(@PathVariable Long id) {
        Optional<StockScreener> stock = stockScreenerService.getStockScreenerById(id);
        return stock.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StockScreener> createStock(@RequestBody StockScreener stock) {
        StockScreener savedStock = stockScreenerService.saveStockScreener(stock);
        return ResponseEntity.ok(savedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        if (stockScreenerService.getStockScreenerById(id).isPresent()) {
            stockScreenerService.deleteStockScreener(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<StockScreener>> filterStocks(@RequestParam(required = false) Boolean halalCompliant) {
        List<StockScreener> filteredStocks = stockScreenerService.filterStocksByHalalStatus(halalCompliant);
        return ResponseEntity.ok(filteredStocks);
    }
}
