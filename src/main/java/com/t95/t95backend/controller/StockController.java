package com.t95.t95backend.controller;

import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.entity.User;
import com.t95.t95backend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/stocks")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping (path = "/list")
    public List<Map> getStocksList() {
        return stockService.getStocksList();
    }
}
