package com.isfin.islamicfinancial.repositories;

import com.isfin.islamicfinancial.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByClientId(Long clientId);
}
