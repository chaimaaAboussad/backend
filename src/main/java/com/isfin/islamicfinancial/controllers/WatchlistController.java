package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.entities.Watchlist;
import com.isfin.islamicfinancial.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    // Get all watchlists
    @GetMapping
    public ResponseEntity<List<Watchlist>> getAllWatchlists() {
        List<Watchlist> watchlists = watchlistService.getAllWatchlists();
        return ResponseEntity.ok(watchlists);
    }

    // Get watchlist by ID
    @GetMapping("/{id}")
    public ResponseEntity<Watchlist> getWatchlistById(@PathVariable Long id) {
        Optional<Watchlist> watchlist = watchlistService.getWatchlistById(id);
        return watchlist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new watchlist entry
    @PostMapping
    public ResponseEntity<Watchlist> createWatchlist(@RequestBody Watchlist watchlist) {
        Watchlist savedWatchlist = watchlistService.saveWatchlist(watchlist);
        return ResponseEntity.ok(savedWatchlist);
    }

    // Delete watchlist entry by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable Long id) {
        if (watchlistService.getWatchlistById(id).isPresent()) {
            watchlistService.deleteWatchlist(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get watchlists by user ID (custom endpoint)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Watchlist>> getWatchlistsByUserId(@PathVariable Long userId) {
        List<Watchlist> userWatchlists = watchlistService.getWatchlistsByUserId(userId);
        return ResponseEntity.ok(userWatchlists);
    }
}
