package com.t95.t95backend.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.t95.t95backend.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
    @Query(value = "select s.id, s.name, s.symbol from stocks s ", nativeQuery = true)
    List<Map> getStocksList();
    
    
//	@Query("SELECT s FROM Stock s WHERE s.symbol = ?1")
	Optional<Stock> findStockBySymbol(String symbol);
    
    @Query(value = "SELECT * "
			+ "FROM watched_stocks ws "
			+ "LEFT JOIN stocks s "
			+ "ON ws.stock_id = s.id "
			+ "WHERE ws.watchlist_id = ?1 ", nativeQuery = true)
	List<Stock> getWatchedStockByWatchlistId(Long watchlistId);
    
    
    @Query(value = "SELECT EXISTS (SELECT * FROM watched_stocks ws WHERE ws.watchlist_id = ?1 AND ws.stock_id = ?2)", nativeQuery = true)
	Boolean findWatchedPair(Long watchlistId, Long stockId);


    @Modifying
	@Query(value = "INSERT INTO watched_stocks (watchlist_id, stock_id) VALUES ( ?1, ?2 )", nativeQuery = true)
    public Integer addNewWatchedStock(Long watchlistId, Long stockId);


    @Modifying
	@Query(value = "DELETE FROM watched_stocks WHERE watchlist_id= ?1 AND stock_id= ?2", nativeQuery = true)
	void deleteWatchedStock(Long watchlistId, Long stockId);






}
