package com.t95.t95backend.repository;

import com.t95.t95backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "select s.id, s.name, s.stock_code from stocks s ", nativeQuery = true)
    List<Map> getStocksList();
}
