package com.isfin.islamicfinancial.repositories;

import com.isfin.islamicfinancial.entities.StockScreener;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockScreenerRepository extends JpaRepository<StockScreener, Long> {
    List<StockScreener> findByShariahCompliant(Boolean shariahCompliant);
}
