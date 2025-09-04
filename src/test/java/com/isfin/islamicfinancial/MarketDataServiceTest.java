package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.CompanyProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MarketDataServiceTest {

    @Autowired
    private MarketDataService marketDataService;

    @Test
    void testFetchCompanyProfile() {
        // Replace with a valid ticker
        String ticker = "AAPL";

        CompanyProfile profile = marketDataService.fetchCompanyProfile(ticker);

        System.out.println(profile); // Prints the fetched data

        // Basic assertion to check if the service returns a profile
        assertNotNull(profile);
        assertNotNull(profile.getSymbol());
    }
}
