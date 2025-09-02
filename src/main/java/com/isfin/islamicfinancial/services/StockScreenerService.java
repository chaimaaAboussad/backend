package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.StockScreener;
import com.isfin.islamicfinancial.repositories.StockScreenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockScreenerService {

    private final StockScreenerRepository stockScreenerRepository;

    @Autowired
    public StockScreenerService(StockScreenerRepository stockScreenerRepository) {
        this.stockScreenerRepository = stockScreenerRepository;
    }

    public List<StockScreener> getAllStockScreeners() {
        return stockScreenerRepository.findAll();
    }

    public Optional<StockScreener> getStockScreenerById(Long id) {
        return stockScreenerRepository.findById(id);
    }

    public StockScreener saveStockScreener(StockScreener stockScreener) {
        return stockScreenerRepository.save(stockScreener);
    }

    public void deleteStockScreener(Long id) {
        stockScreenerRepository.deleteById(id);
    }

    public List<StockScreener> filterStocksByHalalStatus(Boolean halalCompliant) {
        if (halalCompliant == null) {
            return getAllStockScreeners();
        }
        return stockScreenerRepository.findByShariahCompliant(halalCompliant);
    }
}
