package com.t95.t95backend.service;

import com.t95.t95backend.dto.YahooFinanceDTO;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class RetrieveYahooFinanceService {

    private final List<String> stocksToRefresh = Arrays.asList("2330.TW","2317.TW");
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Long refreshPeriod = 180L;

    public YahooFinanceDTO findStock(final String ticker) {
        try {
            String endpoint = "https://query1.finance.yahoo.com/v8/finance/chart/" + ticker;
            RestTemplate restTemplate = new RestTemplate();
            YahooFinanceDTO stock = restTemplate.getForObject(endpoint, YahooFinanceDTO.class);
            return stock;
        } catch(HttpStatusCodeException e) {
            System.out.println(e.getResponseBodyAsString());
        }
        return null;
    }

    public List<YahooFinanceDTO> findStocks(final List<String> tickers) {
        return tickers.stream().map(this::findStock).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Double findPrice(final YahooFinanceDTO stock) {
        double price = stock.getChart().getResult().get(0).getMeta().getRegularMarketPrice();
        return price;
    }

    public String findSymbol(final YahooFinanceDTO stock) {
        String symbol = stock.getChart().getResult().get(0).getMeta().getSymbol();
        return symbol;
    }

    public Double findPreviousClose(final YahooFinanceDTO stock) {
        double previousClose = stock.getChart().getResult().get(0).getMeta().getPreviousClose();
        return previousClose;
    }


    //scheduled executor service with one thread pool
    @EventListener(ApplicationReadyEvent.class)
    public void shouldRefreshEvery3Minutes() throws InterruptedException {
        scheduler.scheduleAtFixedRate(() ->
                stocksToRefresh.forEach((ticker) -> {
                    try {
                        YahooFinanceDTO stock = findStock(ticker);
                        double price = findPrice(stock);
                        String symbol = findSymbol(stock);
                        double previousClose = findPreviousClose(stock);
                        System.out.println("Symbol:" + symbol + " Now:" + price + " Previous" + previousClose);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }), 0, refreshPeriod, SECONDS);
    }
}
