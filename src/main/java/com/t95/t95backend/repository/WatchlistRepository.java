package com.t95.t95backend.repository;

import com.t95.t95backend.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
}
