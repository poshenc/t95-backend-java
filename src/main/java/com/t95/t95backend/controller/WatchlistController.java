package com.t95.t95backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.WatchlistService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@RestController
@RequestMapping(path = "api/watchlists")
public class WatchlistController {

    private final WatchlistService watchlistService;
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    public WatchlistController(WatchlistService watchlistService, JwtTokenUtils jwtTokenUtils) {
        this.watchlistService = watchlistService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    //get all watchLists
    @GetMapping(path = "/all")
    public ResponseEntity getWatchlists(@RequestHeader("Authorization") String authorization) {  
    	try {    		
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
		 	
	    	List<Watchlist> watchlist = watchlistService.getWatchlists();
	    	return ResponseEntity.status(HttpStatus.OK).body(watchlist);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }       

    //get all watchLists by user
    @GetMapping(path = "")
    public ResponseEntity getWatchlistsByUserId(@RequestHeader("Authorization") String authorization) {
		try {			
	    	//JWT: verify and parse JWT token includes user info
			ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
			
	    	List<Watchlist> watchlists = watchlistService.getWatchlistsByUserId(userInfo.getId());
	    		    	if(watchlists.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);    	
	    	return ResponseEntity.status(HttpStatus.OK).body(watchlists);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
    }

    //add new watchList by user
    @PostMapping(path = "")
    public ResponseEntity addNewWatchlist(@RequestHeader("Authorization") String authorization,
            @RequestParam(required = true) String name
            ){
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		watchlistService.addWatchlist(userInfo.getId(), name);
    		return ResponseEntity.status(HttpStatus.OK).body("\"success added watchlist.\"");
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    	        
    }

    //delete watchList by user
    @DeleteMapping(path = "")
    public ResponseEntity deleteWatchlist(@RequestHeader("Authorization") String authorization,
            @RequestParam(required = true) String name
    ){
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		watchlistService.deleteWatchlist(userInfo.getId(), name);
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"success deleted watchlist.\"");
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }
        
}
