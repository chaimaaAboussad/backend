package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.ETF;
import com.isfin.islamicfinancial.entities.CompanyProfile;
import com.isfin.islamicfinancial.repositories.ETFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ETFService {

    private final ETFRepository etfRepository;
    private final MarketDataService marketDataService;

    @Autowired
    public ETFService(ETFRepository etfRepository, MarketDataService marketDataService) {
        this.etfRepository = etfRepository;
        this.marketDataService = marketDataService;
    }

    public List<ETF> getAllETFs() {
        return etfRepository.findAll();
    }

    public Optional<ETF> getETFById(Long id) {
        return etfRepository.findById(id);
    }

    public ETF saveETF(ETF etf) {
        return etfRepository.save(etf);
    }

    public void deleteETF(Long id) {
        etfRepository.deleteById(id);
    }

    // Fetch ETF/company live data using MarketDataService
    public CompanyProfile fetchETFLive(String symbol) {
        return marketDataService.fetchCompanyProfile(symbol);
    }
}
