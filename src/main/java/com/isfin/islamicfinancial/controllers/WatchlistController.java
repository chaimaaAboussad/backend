package com.isfin.islamicfinancial.controllers;

import com.isfin.islamicfinancial.dto.WatchlistRequest;
import com.isfin.islamicfinancial.entities.Watchlist;
import com.isfin.islamicfinancial.services.WatchlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    // Get all watchlists of the logged-in user
    @GetMapping("/me")
    public ResponseEntity<List<Watchlist>> getMyWatchlists() {
        List<Watchlist> watchlists = watchlistService.getMyWatchlists();
        return ResponseEntity.ok(watchlists);
    }

    // Get a specific watchlist by ID
    @GetMapping("/{id}")
    public ResponseEntity<Watchlist> getWatchlistById(@PathVariable Long id) {
        Optional<Watchlist> watchlist = watchlistService.getWatchlistById(id);
        return watchlist.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new watchlist for the logged-in user
    @PostMapping
    public ResponseEntity<Watchlist> createWatchlist(@RequestBody WatchlistRequest request) {
        Watchlist savedWatchlist = watchlistService.createWatchlist(request.getName());
        return ResponseEntity.ok(savedWatchlist);
    }


    // Delete a watchlist by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable Long id) {
        watchlistService.deleteWatchlist(id);
        return ResponseEntity.noContent().build();
    }
}
