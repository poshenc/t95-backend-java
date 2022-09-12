package com.t95.t95backend.controller;

import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/stocks")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Map> getStocksList() {
        return stockService.getStocksList();
    }

    @GetMapping(path = "{stockId}")
    public Optional<Stock> getStocksById(@PathVariable("stockId") Long stockId) {
        return stockService.getStocksById(stockId);
    }

//    @GetMapping (path = "/list")
//    public List<Map> getStocksList() {
//        return stockService.getStocksList();
//    }
}
