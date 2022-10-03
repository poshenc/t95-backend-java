package com.t95.t95backend.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.t95.t95backend.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

	List<Portfolio> findByUserId(Long userId);

	Portfolio findByNameAndUserId(String name, Long userId);

	Portfolio findByUserIdAndName(Long userId, String name);

	@Query(value = "SELECT p.quantity, p.cost_basis, p.open_date, s.id, s.name, s.symbol, s.price, s.movement_points, s.movement_percentage \r\n"
			+ "FROM positions p \r\n"
			+ "LEFT JOIN stocks s \r\n"
			+ "ON p.stock_id = s.id \r\n"
			+ "WHERE p.portfolio_id = ?1 AND p.is_opened = TRUE", nativeQuery = true)
	List<Map> getPortfolioPositionAndPrice(Long portfolioId);

}
