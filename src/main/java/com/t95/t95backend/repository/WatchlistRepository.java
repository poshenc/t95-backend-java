package com.t95.t95backend.repository;

import com.t95.t95backend.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    @Query(value = "select * from watchlists wl where wl.user_id=?1", nativeQuery = true)
    List<Watchlist> getWatchlistsByUserId(Long userId);

}
