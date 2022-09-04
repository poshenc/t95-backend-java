package com.t95.t95backend.repository;

import com.t95.t95backend.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    @Query(value = "select * from watchlists wl where wl.user_id=?1", nativeQuery = true)
    List<Watchlist> getWatchlistsByUserId(Long userId);

    @Query(value = "SELECT CASE WHEN count(e) > 0 THEN true ELSE false END FROM watchlists e where e.name = ?1 and e.user_id = ?2", nativeQuery = true)
    boolean existsByNameAndUserId(String name, Long userId);

    @Query(value = "select id from watchlists e where e.name = ?1 and e.user_id = ?2", nativeQuery = true)
    int findIdByNameAndUserId(String name, Long userId);
}
