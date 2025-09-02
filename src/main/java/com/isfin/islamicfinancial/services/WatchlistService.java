package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.Watchlist;
import com.isfin.islamicfinancial.repositories.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public List<Watchlist> getAllWatchlists() {
        return watchlistRepository.findAll();
    }

    public Optional<Watchlist> getWatchlistById(Long id) {
        return watchlistRepository.findById(id);
    }

    public Watchlist saveWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public void deleteWatchlist(Long id) {
        watchlistRepository.deleteById(id);
    }

    public List<Watchlist> getWatchlistsByUserId(Long userId) {
        return watchlistRepository.findByClientId(userId);
    }
}
