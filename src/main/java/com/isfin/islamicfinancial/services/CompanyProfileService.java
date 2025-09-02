package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.CompanyProfile;
import com.isfin.islamicfinancial.repositories.CompanyProfileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CompanyProfileService {

    private final CompanyProfileRepository repository;
    private final RestTemplate restTemplate;

    @Value("${fmp.api.base-url}")
    private String fmpApiBaseUrl;

    @Value("${fmp.api.key}")
    private String fmpApiKey;

    public CompanyProfileService(CompanyProfileRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    // Fetch profile from FMP v5 and save it to the database
    public CompanyProfile fetchAndSaveProfile(String symbol) {
        String url = fmpApiBaseUrl + "company-profile?symbol=" + symbol + "&apikey=" + fmpApiKey;

        // v5 returns a JSON object, not an array
        ResponseEntity<CompanyProfile> response = restTemplate.getForEntity(url, CompanyProfile.class);

        CompanyProfile profile = response.getBody();

        if (profile != null) {
            return repository.save(profile);
        } else {
            throw new RuntimeException("Profile not found for symbol: " + symbol);
        }
    }

    // Retrieve a profile from the database
    public Optional<CompanyProfile> getProfileBySymbol(String symbol) {
        return repository.findById(symbol);
    }

    public CompanyProfile fetchProfileLive(String symbol) {
        String url = fmpApiBaseUrl + "profile/" + symbol + "?apikey=" + fmpApiKey;
        ResponseEntity<CompanyProfile[]> response = restTemplate.getForEntity(url, CompanyProfile[].class);

        CompanyProfile[] profiles = response.getBody();
        return (profiles != null && profiles.length > 0) ? profiles[0] : null;
    }

}

