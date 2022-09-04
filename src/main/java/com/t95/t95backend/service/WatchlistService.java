package com.t95.t95backend.service;

import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    public List<Watchlist> getWatchlists() { return watchlistRepository.findAll(); }

    public List<Watchlist> getWatchlistsByUserId(Long userId) {
        return watchlistRepository.getWatchlistsByUserId(userId); }

    public void addWatchlist(Long userId, String name) {
        Watchlist watchlist = new Watchlist(name, userId);
        watchlistRepository.save(watchlist);
    }
}
