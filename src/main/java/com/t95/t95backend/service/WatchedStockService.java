package com.t95.t95backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.t95.t95backend.repository.WatchedStockRepository;

@Service
public class WatchedStockService {
	
	private final WatchedStockRepository watchedStockRepository;
		
	public WatchedStockService(WatchedStockRepository watchedStockRepository) {
		super();
		this.watchedStockRepository = watchedStockRepository;
	}

	public List<Map> getWatchedStockByWatchlistId(Long watchlistId) {		
		return watchedStockRepository.getWatchedStockByWatchlistId(watchlistId);
	}

}