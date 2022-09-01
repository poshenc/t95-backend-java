package com.t95.t95backend.config;

import com.t95.t95backend.entity.Watchlist;
import com.t95.t95backend.repository.WatchlistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WatchlistConfig {

    @Bean
    CommandLineRunner commandLineWatchlist (WatchlistRepository watchlistRepository) {
        return Args -> {
            Watchlist USstock = new Watchlist(
                    1,
                    "US stock",
                    "NVDA, TSLA, AAPL"
            );
            Watchlist TWstock = new Watchlist(
                    1,
                    "TW stock",
                    "台積電,聯電,鴻海"
            );
            Watchlist crypto = new Watchlist(
                    1,
                    "Crypto",
                    "BTC, ETH, ADA"
            );
            watchlistRepository.saveAll(List.of(USstock, TWstock, crypto));
        };
    }
}
