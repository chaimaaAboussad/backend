package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.CompanyProfile;
import com.isfin.islamicfinancial.services.CompanyProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companyProfiles")
public class CompanyProfileController {

    private final CompanyProfileService service;

    public CompanyProfileController(CompanyProfileService service) {
        this.service = service;
    }

    /**
     * Get a saved profile or fetch live from combined market APIs
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<CompanyProfile> getProfile(@PathVariable String symbol,
                                                     @RequestParam(defaultValue = "false") boolean live) {
        CompanyProfile profile;

        if (live) {
            profile = service.fetchProfileLive(symbol);
        } else {
            profile = service.fetchAndSaveProfile(symbol);
        }

        return ResponseEntity.ok(profile);
    }
    // Get all company profiles
    @GetMapping("/all")
    public List<CompanyProfile> getAllCompanies() {
        return service.getAllCompanyProfiles();
    }

}
