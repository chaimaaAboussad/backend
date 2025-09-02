package com.isfin.islamicfinancial.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Watchlist> watchlist;

    public Client() {
        super();
    }

    public List<Watchlist> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(List<Watchlist> watchlist) {
        this.watchlist = watchlist;
    }
}