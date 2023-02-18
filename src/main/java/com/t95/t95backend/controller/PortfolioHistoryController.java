package com.t95.t95backend.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.t95.t95backend.entity.PortfolioHistory;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.PortfolioHistoryService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@RestController
@RequestMapping(path = "api/portfolioHistory")
public class PortfolioHistoryController {
	
    private final PortfolioHistoryService portfolioHistoryService;
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    public PortfolioHistoryController(PortfolioHistoryService portfolioHistoryService, JwtTokenUtils jwtTokenUtils) {
        super();
        this.portfolioHistoryService = portfolioHistoryService;
        this.jwtTokenUtils=jwtTokenUtils;
    }

    //get all portfolios value by date and by user
    @GetMapping(path = "/allPortfoliosByDate")
    public ResponseEntity getAllPortfoliosValueByDate(@RequestHeader("Authorization") String authorization, 
    	@RequestParam(required = true) String date) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		LocalDate localDate = LocalDate.parse(date);
		
    		Optional<List<PortfolioHistory>> portfolioValue = portfolioHistoryService.getAllPortfoliosValueByDate(userInfo.getId(), localDate);
    		return ResponseEntity.status(HttpStatus.OK).body(portfolioValue);    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    	
    }
    
    //get all portfolios values by date range and by user
    @GetMapping(path = "/allPortfoliosByDateBetween")
    public ResponseEntity getAllPortfoliosValueByDateRange(@RequestHeader("Authorization") String authorization,
    		@RequestParam(required = true) String dateStart, @RequestParam(required = true) String dateEnd) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		LocalDate startDate = LocalDate.parse(dateStart);
    		LocalDate endDate = LocalDate.parse(dateEnd);
		
    		Optional<List<PortfolioHistory>> portfolioValues = portfolioHistoryService.getAllPortfoliosValueByDateRange(userInfo.getId(), startDate, endDate);        
    		return ResponseEntity.status(HttpStatus.OK).body(portfolioValues);    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    
    }
    
  //get single portfolio value by date and by user
    @GetMapping(path = "/portfolioByDate")
    public ResponseEntity getPortfolioValueByDateAndPortfolioId(@RequestHeader("Authorization") String authorization, 
    		@RequestParam(required = true) Long portfolioId, @RequestParam(required = true) String date) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		LocalDate localDate = LocalDate.parse(date);
		
    		Optional<PortfolioHistory> portfolioValue = portfolioHistoryService.getPortfolioValueByDateAndPortfolioId(userInfo.getId(), portfolioId, localDate);       
    		return ResponseEntity.status(HttpStatus.OK).body(portfolioValue);    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    	
    }
    
    //get single portfolio value by date range and by user
    @GetMapping(path = "/portfolioByDateBetween")
    public ResponseEntity getPortfolioValueByDateRangeAndPortfolioId(@RequestHeader("Authorization") String authorization,
    		@RequestParam(required = true) Long portfolioId, @RequestParam(required = true) String dateStart, @RequestParam(required = true) String dateEnd) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		LocalDate startDate = LocalDate.parse(dateStart);
    		LocalDate endDate = LocalDate.parse(dateEnd);
		
    		Optional<List<PortfolioHistory>> portfolioValues = portfolioHistoryService.getPortfolioValueByDateRangeAndPortfolioId(userInfo.getId(), portfolioId, startDate, endDate);       
    		return ResponseEntity.status(HttpStatus.OK).body(portfolioValues);    		
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    
    }
}
