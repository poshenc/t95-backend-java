package com.t95.t95backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.t95.t95backend.service.WatchedStockService;

@RestController
@RequestMapping(path = "api/watchedStocks")
public class WatchedStockController {
	private final WatchedStockService watchedStockService;

	@Autowired
	public WatchedStockController(WatchedStockService watchedStockService) {
		super();
		this.watchedStockService = watchedStockService;
	}
    
    @GetMapping(path = "{watchlistId}")
    public ResponseEntity getWatchedStocksByWatchlistId(
    		@PathVariable("watchlistId") Long watchlistId) {
    	List<Map> watchedStocks = watchedStockService.getWatchedStockByWatchlistId(watchlistId);
    	
    	List<HashMap<String, String>> returnWatchlist = new ArrayList<HashMap<String, String>>();

		for (Map watchedStock : watchedStocks) {
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("name", watchedStock.get("name").toString());
			map.put("symbol", watchedStock.get("stock_code").toString());
			map.put("price", watchedStock.get("price").toString());
			map.put("movementPrice", watchedStock.get("movement_points").toString());
			map.put("movementPercentage", watchedStock.get("movement_percentage").toString());
			returnWatchlist.add(map);
		}

    	return ResponseEntity.status(HttpStatus.OK).body(returnWatchlist);
    }
	
    @Transactional
    @PostMapping(path = "{watchlistId}")
    public void addNewWatchedStock(
            @PathVariable("watchlistId") Long watchlistId,
            @RequestParam(required = true) Long stockId
            ){
    	watchedStockService.addNewWatchedStock(watchlistId, stockId);
    }
    
    @Transactional
    @DeleteMapping(path = "{watchlistId}")
    public ResponseEntity deleteWatchedStock(
    		@PathVariable("watchlistId") Long watchlistId,
            @RequestParam(required = true) Long stockId
    		) {
    	watchedStockService.deleteWatchedStock(watchlistId, stockId);
    	return ResponseEntity.status(HttpStatus.OK).body("\"success delete watched stock.\"");
    }
	

}
