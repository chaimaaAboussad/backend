package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.ETF;
import com.isfin.islamicfinancial.repositories.ETFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ETFService {

    private final ETFRepository etfRepository;
    private final RestTemplate restTemplate;

    @Value("${fmp.api.base-url}")
    private String fmpApiBaseUrl;

    @Value("${fmp.api.key}")
    private String fmpApiKey;

    @Autowired
    public ETFService(ETFRepository etfRepository) {
        this.etfRepository = etfRepository;
        this.restTemplate = new RestTemplate(); // initialize RestTemplate
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

    // Fetch live ETF data from FMP API
    public ETF fetchETFLive(String symbol) {
        String url = fmpApiBaseUrl + "etf-profile/" + symbol + "?apikey=" + fmpApiKey;
        try {
            ETF[] etfs = restTemplate.getForObject(url, ETF[].class);
            return (etfs != null && etfs.length > 0) ? etfs[0] : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
