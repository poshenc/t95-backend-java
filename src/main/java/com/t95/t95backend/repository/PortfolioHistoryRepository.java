package com.t95.t95backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.t95.t95backend.entity.PortfolioHistory;

public interface PortfolioHistoryRepository extends JpaRepository<PortfolioHistory, Long> {	

	Optional<List<PortfolioHistory>> findByUserIdAndDate(Long userId, LocalDate date);
	
	Optional<List<PortfolioHistory>> findAllByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

	@Query(value = "select * from portfolio_history p where p.user_id = ?1 and p.portfolio_id = ?2 and p.date = ?3", nativeQuery = true)
	Optional<PortfolioHistory> findByUserIdAndPortfolioIdAndDate(Long userId, Long portfolioId, LocalDate date);

	@Query(value = "select * from portfolio_history p where p.user_id = ?1 and p.portfolio_id = ?2 and date >= ?3 and date <= ?4", nativeQuery = true)
	Optional<List<PortfolioHistory>> findAllByUserIdAndPortfolioIdAndDateBetween(Long userId, Long portfolioId,
			LocalDate startDate, LocalDate endDate);

}


