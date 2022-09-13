package com.t95.t95backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.t95.t95backend.entity.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

//    @Query(value = "select * from watchLists wl where wl.user_id=?1", nativeQuery = true)
	public List<Watchlist> findByUserId(Long userId);

    @Query(value = "SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM watchlists e where e.name = ?1 and e.user_id = ?2", nativeQuery = true)
    boolean existsByNameAndUserId(String name, Long userId);

    @Query(value = "select * from watchlists e where e.name = ?1 and e.user_id = ?2", nativeQuery = true)
    Watchlist findByNameAndUserId(String name, Long userId);

    

	
}
