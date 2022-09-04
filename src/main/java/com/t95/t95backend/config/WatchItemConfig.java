//package com.t95.t95backend.config;
//
//import com.t95.t95backend.entity.WatchItem;
//import com.t95.t95backend.entity.Watchlist;
//import com.t95.t95backend.repository.WatchItemRepository;
//import com.t95.t95backend.repository.WatchlistRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class WatchItemConfig {
//
//    @Bean
//    CommandLineRunner commandLineWatchItem (WatchItemRepository watchItemRepository) {
//        return Args -> {
//            WatchItem watchItem1 = new WatchItem(
//                    1,
//                    "NVDA"
//            );
//            WatchItem watchItem2 = new WatchItem(
//                    1,
//                    "TSLA"
//            );
//            WatchItem watchItem3 = new WatchItem(
//                    1,
//                    "AAPL"
//            );
////            WatchItem watchItem2 = new WatchItem(
////                    1,
////                    "TW stock",
////                    "台積電,聯電,鴻海"
////            );
////            WatchItem watchItem3 = new WatchItem(
////                    1,
////                    "Crypto",
////                    "BTC, ETH, ADA"
////            );
//            watchItemRepository.saveAll(List.of(watchItem1, watchItem2, watchItem3));
//        };
//    }
//}
