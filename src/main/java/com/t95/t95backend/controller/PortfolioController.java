package com.t95.t95backend.controller;

import java.util.List;
import java.util.Optional;

import com.t95.t95backend.bean.PositionBean;
import com.t95.t95backend.entity.Position;
import com.t95.t95backend.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.t95.t95backend.bean.PortfolioBean;
import com.t95.t95backend.entity.Portfolio;
import com.t95.t95backend.returnBean.ReturnPosition;
import com.t95.t95backend.returnBean.ReturnUserInfo;
import com.t95.t95backend.service.PortfolioService;
import com.t95.t95backend.utils.encryption.JwtTokenUtils;

@RestController
@RequestMapping(path = "api/portfolios")
public class PortfolioController {
	
    private final PortfolioService portfolioService;
	private final PositionService positionService;
    private JwtTokenUtils jwtTokenUtils;
    
    @Autowired
	public PortfolioController(PortfolioService portfolioService, PositionService positionService, JwtTokenUtils jwtTokenUtils) {
		this.portfolioService = portfolioService;
		this.positionService = positionService;
		this.jwtTokenUtils = jwtTokenUtils;
	}
    
  //get all portfolios
    @GetMapping(path = "/all")
    public ResponseEntity getPortfolios(@RequestHeader("Authorization") String authorization) {  
    	try {    		
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
		 	
	    	List<Portfolio> portfolios = portfolioService.getPortfolios();
	    	return ResponseEntity.status(HttpStatus.OK).body(portfolios);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }   
    
    //get all portfolios by user
    @GetMapping(path = "")
    public ResponseEntity getPortfoliosByUserId(@RequestHeader("Authorization") String authorization) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		List<Portfolio> portfolios = portfolioService.getPortfoliosByUserId(userInfo.getId());
    		if(portfolios.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    	return ResponseEntity.status(HttpStatus.OK).body(portfolios);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }
    
    //get single portfolio by portfolioId
    @GetMapping(path = "/{portfolioId}")
    public ResponseEntity getPortfolioByPortfolioId(@RequestHeader("Authorization") String authorization,
    		@PathVariable(required = true) Long portfolioId) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		Optional<Portfolio> portfolio = portfolioService.getPortfolioByPortfolioId(portfolioId);
    		if(portfolio.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	    	return ResponseEntity.status(HttpStatus.OK).body(portfolio);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }
    
  //add new portfolio by user
    @PostMapping(path = "")
    public ResponseEntity addNewPortfolio(@RequestHeader("Authorization") String authorization,
    		@RequestBody PortfolioBean portfolioBean
            ){
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		portfolioService.addPortfolio(userInfo.getId(), portfolioBean.getName());
    		return ResponseEntity.status(HttpStatus.OK).body("\"success added portfolio.\"");
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    	        
    }
    
  //delete portfolio by user
    @DeleteMapping(path = "")
    public ResponseEntity deletePortfolio(@RequestHeader("Authorization") String authorization,
            @RequestParam(required = true) String name
    ){
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		portfolioService.deletePortfolio(userInfo.getId(), name);
    		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("\"success deleted portfolio.\"");
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }
    
    //edit cash or name of a portfolio
    @PutMapping(path = "/{name}")
    public ResponseEntity editPortfolio(@RequestHeader("Authorization") String authorization,
    		@PathVariable(required = true) String name, @RequestBody PortfolioBean portfolioBean
            ){
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		Portfolio portfolio = portfolioService.getPortfoliosByUserIdAndName(userInfo.getId(), name);
    		
    		if(portfolioBean.getCash() != null) {
    			portfolio.setCash(portfolioBean.getCash());   			
    		}
    		if(portfolioBean.getName() != null) {
    			portfolio.setName(portfolioBean.getName());		
    		}
    		
    		Portfolio newPortfolio = portfolioService.savePortfolio(portfolio);    		
    		
    		return ResponseEntity.status(HttpStatus.OK).body("\"success edited portfolio.\"");
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}    	        
    }
    
    // ************************* positions and price and movements in portfolio *******************************
    
    //get positions and price of a portfolio
    @GetMapping(path = "{portfolioId}/positions")
    public ResponseEntity getPositionAndPrice(@RequestHeader("Authorization") String authorization,
    		@PathVariable(required = true) Long portfolioId) {
    	try {
    		//JWT: verify and parse JWT token includes user info
    		ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
    		
    		List<ReturnPosition> positionAndPrice = portfolioService.getPortfolioPositionAndPrice(portfolioId);
    		
    		if(positionAndPrice.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);    		
    		
    		return ResponseEntity.status(HttpStatus.OK).body(positionAndPrice);
    	} catch (Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    	}
    }

	//add position to portfolio
	@PostMapping(path = "{portfolioId}/positions")
	public ResponseEntity addPosition(@RequestHeader("Authorization") String authorization,
		@PathVariable (required = true) Long portfolioId, @RequestBody (required = true) PositionBean positionBean) {
		try {
			//JWT: verify and parse JWT token includes user info
			ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);

			Position position = new Position(positionBean.getQuantity(), positionBean.getCostBasis(), positionBean.getOpenDate(), portfolioId, positionBean.getStockId());
			positionService.savePosition(position);
			return ResponseEntity.status(HttpStatus.OK).body("\"success added position.\"");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	//edit a position in portfolio
	@PutMapping(path = "{portfolioId}/positions")
	public ResponseEntity editPosition(@RequestHeader("Authorization") String authorization,
			@PathVariable (required = true) Long portfolioId, @RequestBody (required = true) PositionBean positionBean) {
		try {
			//JWT: verify and parse JWT token includes user info
			ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
			
			Optional<Position> positionEntity = positionService.getPosition(positionBean.getPositionId());
			if(positionEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			Position position = positionEntity.get();
			if(positionBean.getQuantity() != null) { position.setQuantity(positionBean.getQuantity()); }
			if(positionBean.getCostBasis() != null) { position.setCostBasis(positionBean.getCostBasis()); }
			if(positionBean.getOpenDate() != null) { position.setOpenDate(positionBean.getOpenDate()); }
			positionService.savePosition(position);
			
			return ResponseEntity.status(HttpStatus.OK).body("\"success edited position.\"");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	//close position from portfolio
	@DeleteMapping(path = "{portfolioId}/positions")
	public ResponseEntity closePosition(@RequestHeader("Authorization") String authorization,
			@PathVariable (required = true) Long portfolioId, @RequestBody (required = true) PositionBean positionBean) {
		try {
			//JWT: verify and parse JWT token includes user info
			ReturnUserInfo userInfo = jwtTokenUtils.getJwtInfo(authorization);
			
			Optional<Position> positionEntity = positionService.getPosition(positionBean.getPositionId());
			if(positionEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
			Position position = positionEntity.get();
			position.setCloseDate(positionBean.getCloseDate());
			position.setIsOpened(false);
			positionService.savePosition(position);
			
			return ResponseEntity.status(HttpStatus.OK).body("\"success closed position.\"");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
