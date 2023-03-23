package com.t95.t95backend.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.t95.t95backend.entity.PortfolioHistory;
import com.t95.t95backend.repository.PortfolioHistoryRepository;

@Service
public class PortfolioHistoryService {
    
    private final PortfolioHistoryRepository portfolioHistoryRepository;

	public PortfolioHistoryService(PortfolioHistoryRepository portfolioHistoryRepository) {
		this.portfolioHistoryRepository = portfolioHistoryRepository;
	}
    
    public Optional<List<PortfolioHistory>> getAllPortfoliosValueByDate(Long userId, LocalDate date) { 
    	return portfolioHistoryRepository.findByUserIdAndDate(userId, date); 
    }

    public Optional<List<PortfolioHistory>> getAllPortfoliosValueByDateRange(Long userId, LocalDate startDate, LocalDate endDate) { 
    	return portfolioHistoryRepository.findAllByUserIdAndDateBetween(userId, startDate, endDate); 
    }

	public Date findEarliestDateOfPortfolio(Long userId, Long portfolioId) {
		return portfolioHistoryRepository.findEarliestDateOfPortfolio(userId, portfolioId);
	}

	public Optional<PortfolioHistory> getPortfolioValueByDateAndPortfolioId(Long userId, Long portfolioId,
			LocalDate date) {
		return portfolioHistoryRepository.findByUserIdAndPortfolioIdAndDate(userId, portfolioId, date); 
	}

	public Optional<List<PortfolioHistory>> getPortfolioValueByDateRangeAndPortfolioId(Long userId, Long portfolioId,
			LocalDate startDate, LocalDate endDate) {
		return portfolioHistoryRepository.findAllByUserIdAndPortfolioIdAndDateBetween(userId, portfolioId, startDate, endDate); 
	}

	public PortfolioHistory savePortfolioHistory(PortfolioHistory portfolioHistory) {
		try {
			return this.portfolioHistoryRepository.save(portfolioHistory);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Something went wrong.");
		}
	}
}
