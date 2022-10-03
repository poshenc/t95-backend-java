package com.t95.t95backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.repository.StockRepository;
import com.t95.t95backend.repository.WatchlistRepository;

@Service
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final StockRepository stockRepository;

    @Autowired
    public WatchlistService(WatchlistRepository watchlistRepository,StockRepository stockRepository) {
        this.watchlistRepository = watchlistRepository;
		this.stockRepository = stockRepository;
    }

    public List<Watchlist> getWatchlists() { return watchlistRepository.findAll(); }

    public List<Watchlist> getWatchlistsByUserId(Long userId) {
    	return watchlistRepository.findByUserId(userId);
    }

    public void addWatchlist(Long userId, String name) {
    	Watchlist exisitngWatchlist = watchlistRepository.findByNameAndUserId(name, userId);
    	if(exisitngWatchlist != null) {
        	throw new IllegalStateException("Duplicated watchlist name!");
        }
    	
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

    public Watchlist findByNameAndUserId(String name, Long userId) {
        return watchlistRepository.findByNameAndUserId(name, userId);
    }

}
