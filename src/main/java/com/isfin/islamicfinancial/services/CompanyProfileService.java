package com.isfin.islamicfinancial.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.isfin.islamicfinancial.entities.CompanyProfile;
import com.isfin.islamicfinancial.repositories.CompanyProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyProfileService {

    private final CompanyProfileRepository repository;
    private final MarketDataService marketDataService;

    public CompanyProfileService(CompanyProfileRepository repository,
                                 MarketDataService marketDataService) {
        this.repository = repository;
        this.marketDataService = marketDataService;
    }

    /**
     * Fetches combined market data for a symbol and saves/updates CompanyProfile in DB
     */
    public CompanyProfile fetchAndSaveProfile(String symbol) {
        // Call combined API
        JsonNode combined = marketDataService.getCombinedData(symbol);

        JsonNode alpha = combined.get("alpha");
        JsonNode polygon = combined.get("polygon");

        // Parse data safely (use defaults if missing)
        Double price = alpha.path("Global Quote").path("05. price").asDouble(0);
        Double marketCap = polygon.path("results").get(0).path("marketCap").asDouble(0);

        String companyName = polygon.path("results").get(0).path("ticker").asText(symbol);
        String industry = polygon.path("results").get(0).path("industry").asText("");
        String sector = polygon.path("results").get(0).path("sector").asText("");

        CompanyProfile profile = repository.findById(symbol).orElse(new CompanyProfile());
        profile.setSymbol(symbol);
        profile.setPrice(price);
        profile.setMktCap(marketCap != 0 ? marketCap.longValue() : null);
        profile.setCompanyName(companyName);
        profile.setIndustry(industry);
        profile.setSector(sector);

        return repository.save(profile);
    }

    public Optional<CompanyProfile> getProfileBySymbol(String symbol) {
        return repository.findById(symbol);
    }

    public CompanyProfile fetchProfileLive(String symbol) {
        JsonNode combined = marketDataService.getCombinedData(symbol);

        JsonNode alpha = combined.get("alpha");
        JsonNode polygon = combined.get("polygon");

        Double price = alpha.path("Global Quote").path("05. price").asDouble(0);
        Double marketCap = polygon.path("results").get(0).path("marketCap").asDouble(0);

        String companyName = polygon.path("results").get(0).path("ticker").asText(symbol);
        String industry = polygon.path("results").get(0).path("industry").asText("");
        String sector = polygon.path("results").get(0).path("sector").asText("");

        CompanyProfile profile = new CompanyProfile();
        profile.setSymbol(symbol);
        profile.setPrice(price);
        profile.setMktCap(marketCap != 0 ? marketCap.longValue() : null);
        profile.setCompanyName(companyName);
        profile.setIndustry(industry);
        profile.setSector(sector);

        return profile;
    }
}
