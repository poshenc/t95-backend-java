package com.t95.t95backend.service;

import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Map> getStocksList() {
        return stockRepository.getStocksList();
    }
}
