package com.isfin.islamicfinancial.services;

import com.isfin.islamicfinancial.entities.User;
import com.isfin.islamicfinancial.entities.Watchlist;
import com.isfin.islamicfinancial.repositories.UserRepository;
import com.isfin.islamicfinancial.repositories.WatchlistRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;

    public WatchlistService(WatchlistRepository watchlistRepository, UserRepository userRepository) {
        this.watchlistRepository = watchlistRepository;
        this.userRepository = userRepository;
    }

    public List<Watchlist> getAllWatchlists() {
        return watchlistRepository.findAll();
    }

    public Optional<Watchlist> getWatchlistById(Long id) {
        return watchlistRepository.findById(id);
    }

    public Watchlist createWatchlist(String name) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Watchlist watchlist = new Watchlist(name, user);
        return watchlistRepository.save(watchlist);
    }

    public void deleteWatchlist(Long id) {
        watchlistRepository.deleteById(id);
    }

    public List<Watchlist> getMyWatchlists() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        return watchlistRepository.findByUserId(user.getId());
    }
}
