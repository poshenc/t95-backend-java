package com.t95.t95backend.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.repository.StockRepository;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Map> getStocksList() {
        return stockRepository.getStocksList();
    }

	public List<String> getSymbolsList() {
		return stockRepository.getSymbolsList();
	}

	public Optional<Stock> findById(Long id) {
		return stockRepository.findById(id);
	}
    
	public Optional<Stock> findStockBySymbol(String symbol) {
		return stockRepository.findStockBySymbol(symbol);
	}

	public List<Stock> getWatchedStockByWatchlistId(Long watchlistId) {
		return stockRepository.getWatchedStockByWatchlistId(watchlistId);
	}
	
	public Boolean findWatchedPair(Long watchlistId, Long stockId) {
		return stockRepository.findWatchedPair(watchlistId, stockId);
	}

	public Integer addNewWatchedStock(Long watchlistId, Long stockId) {
		return stockRepository.addNewWatchedStock(watchlistId, stockId);		
	}

	public void deleteWatchedStock(Long watchlistId, Long stockId) {
		stockRepository.deleteWatchedStock(watchlistId, stockId);		
	}


	public void updateStock(Stock stock) {
		stockRepository.save(stock);
	}
}
