package com.t95.t95backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.t95.t95backend.entity.Portfolio;
import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.repository.PortfolioRepository;

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


	public void deleteWatchlist(Long userId, String name) {		
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

}
