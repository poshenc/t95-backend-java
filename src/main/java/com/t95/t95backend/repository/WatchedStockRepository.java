package com.t95.t95backend.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.t95.t95backend.entity.WatchedStock;

public interface WatchedStockRepository extends JpaRepository<WatchedStock, Long> {
	
	
	@Query(value = "SELECT ws.watchlist_id, ws.stock_id, s.id, s.name, s.stock_code, s.price, s.movement_points, s.movement_percentage "
			+ "FROM watched_stocks ws "
			+ "LEFT JOIN stocks s "
			+ "ON ws.stock_id = s.id "
			+ "WHERE ws.watchlist_id = ?1 ", nativeQuery = true)
	List<Map> getWatchedStockByWatchlistId(Long watchlistId);
	

}
