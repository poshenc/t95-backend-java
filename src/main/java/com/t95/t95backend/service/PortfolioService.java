package com.t95.t95backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.t95.t95backend.entity.Position;
import org.springframework.stereotype.Service;

import com.t95.t95backend.entity.Portfolio;
import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.repository.PortfolioRepository;
import com.t95.t95backend.returnBean.ReturnPosition;

@Service
public class PortfolioService {
	
	private final PortfolioRepository portfolioRepository;	

	public PortfolioService(PortfolioRepository portfolioRepository) {
		this.portfolioRepository = portfolioRepository;
	}
	

	public List<Portfolio> getPortfolios() {
		return portfolioRepository.findAll();
	}

	public List<Portfolio> getPortfoliosByUserId(Long userId) {
		return portfolioRepository.findByUserId(userId);
	}


	public void addPortfolio(Long userId, String name) {
		Portfolio exisitngPortfolio = portfolioRepository.findByNameAndUserId(name, userId);
    	if(exisitngPortfolio != null) {
        	throw new IllegalStateException("Duplicated portfolio name!");
        }
    	
    	Portfolio portfolio = new Portfolio(name, userId);
    	portfolioRepository.save(portfolio);		
	}


	public void deletePortfolio(Long userId, String name) {		
		Portfolio portfolio = portfolioRepository.findByNameAndUserId(name, userId);
    	
        if(portfolio == null) {
        	throw new IllegalStateException("portfolio does not exist!");
        }
        
        portfolioRepository.deleteById(portfolio.getId());		
	}


	public Portfolio getPortfoliosByUserIdAndName(Long userId, String name) {
		return portfolioRepository.findByUserIdAndName(userId, name);
	}


	public Portfolio savePortfolio(Portfolio portfolio) {
		return portfolioRepository.save(portfolio);	
	}


	public List<ReturnPosition> getPortfolioPositionAndPrice(Long portfolioId) {		
		List<ReturnPosition> returnPosition = new ArrayList<ReturnPosition>();
		
		List<Map> positions = portfolioRepository.getPortfolioPositionAndPrice(portfolioId);
		
		for (Map<String, Object> p: positions) {
			System.out.println((Date) p.get("open_date"));
			ReturnPosition r = new ReturnPosition();
			r.setPositionId(((Number) p.get("id")).longValue());
			r.setQuantity(((Number) p.get("quantity")).longValue());
			r.setCostBasis(Double.valueOf(p.get("cost_basis").toString()));
			r.setOpenDate(p.get("open_date").toString());
			r.setName((String) p.get("name"));
			r.setSymbol((String) p.get("symbol"));
			r.setPrice((String) p.get("price"));
			r.setMovement_points((String) p.get("movement_points"));
			r.setMovement_percentage((String) p.get("movement_percentage"));
			returnPosition.add(r);
		}
		
		return returnPosition;
		
	}
}
