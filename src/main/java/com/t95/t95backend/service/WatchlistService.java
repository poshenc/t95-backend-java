package com.t95.t95backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.repository.WatchlistRepository;

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

    public void deleteWatchlist(Long userId, String name) {
    	Watchlist watchlist = watchlistRepository.findByNameAndUserId(name, userId);
    	
        if(watchlist == null) {
        	throw new IllegalStateException("watchlist does not exist!");
        }
        
        watchlistRepository.deleteById(watchlist.getId());
    }

}
