package com.t95.t95backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.StockService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@RestController
@RequestMapping(path = "api/stocks")
public class StockController {

    private StockService stockService;
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    public StockController(StockService stockService, JwtTokenUtils jwtTokenUtils) {
        this.stockService = stockService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    //get all stocks names and id
    @GetMapping
    public ResponseEntity getStocksList(@RequestHeader("Authorization") String authorization) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		List<Map> list = stockService.getStocksList();
    		return ResponseEntity.status(HttpStatus.OK).body(list);
    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }

    //get a stock current price and movements
    @GetMapping(path = "{symbol}")
    public ResponseEntity findStockBySymbol(@RequestHeader("Authorization") String authorization, @PathVariable("symbol") String symbol) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		Optional<Stock> stock = stockService.findStockBySymbol(symbol);
        	
        	if(stock.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    		return ResponseEntity.status(HttpStatus.OK).body(stock);
    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }   
    
}
