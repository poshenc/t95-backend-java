package com.t95.t95backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
			map.put("stock_code", watchedStock.get("stock_code").toString());
			map.put("price", watchedStock.get("price").toString());
			map.put("movement_points", watchedStock.get("movement_points").toString());
			map.put("movement_percentage", watchedStock.get("movement_percentage").toString());
			returnWatchlist.add(map);
		}
//    	return returnWatchlist;
    	return ResponseEntity.status(HttpStatus.OK).body(returnWatchlist);
//        return watchedStockService.getWatchedStockByWatchlistId(watchlistId);
    }
	
	

}
