package com.t95.t95backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.t95.t95backend.service.WatchedStockService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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


//    	return returnWatchlist;
    	return ResponseEntity.status(HttpStatus.OK).body(returnWatchlist);
//        return watchedStockService.getWatchedStockByWatchlistId(watchlistId);
    }
	
	

}
