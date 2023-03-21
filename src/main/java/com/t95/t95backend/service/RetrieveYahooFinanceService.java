package com.t95.t95backend.service;

import com.t95.t95backend.dto.YahooFinanceDTO;
import com.t95.t95backend.entity.Stock;
import com.t95.t95backend.entity.User;
import com.t95.t95backend.repository.StockRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class RetrieveYahooFinanceService {

    private final List<String> stocksToRefresh = Arrays.asList("^DJI", "^IXIC", "^GSPC", "^GSPC", "TWD=X", "TSLA", "AAPL", "NVDA", "2330.TW", "1229.TW", "2454.TW", "ETH-USD", "SOL-USD", "BTC-USD");
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Long refreshPeriodInSeconds = 6000L;

    private StockRepository stockRepository;

    public RetrieveYahooFinanceService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

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
    public void shouldRefreshStockTableEvery100Minutes() throws InterruptedException {
        scheduler.scheduleAtFixedRate(() ->
                stocksToRefresh.forEach((ticker) -> {
                    try {
                        YahooFinanceDTO stock = findStock(ticker);
                        String symbol = findSymbol(stock);
                        double currentPrice = findPrice(stock);
                        double previousClose = findPreviousClose(stock);
                        updateStock(symbol, currentPrice, previousClose);
//                        System.out.println("Symbol:" + symbol + " Now:" + currentPrice + " Previous" + previousClose);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }), 0, refreshPeriodInSeconds, SECONDS);
    }


    @Transactional
    public void updateStock(String symbol, double currentPrice, double previousClose) {
        Optional<Stock> optionalStock = stockRepository.findStockBySymbol(symbol);
        if(optionalStock.isEmpty() || !(currentPrice > 0) || !(previousClose > 0)) {
            throw new IllegalStateException("something wrong with Yahoo Finance data");
        }

        Stock stock = stockRepository.findById(optionalStock.get().getId())
                .orElseThrow(() -> new IllegalStateException("stock with id: " + optionalStock.get().getId() + " does not exist!"));

        String price = String.format( "%.2f", currentPrice );
        if(!Objects.equals(stock.getPrice(), price)) {
            //update price
            stock.setPrice(price);

            //update movementPoints
            String movementPoints = String.format( "%.2f", (currentPrice - previousClose) );
            Boolean isPositive = Character.isDigit(movementPoints.charAt(0));
            stock.setMovementPoints((isPositive ? "+" : "") + movementPoints);

            //update calcPercentage
            Double calcPercentage = ((currentPrice - previousClose) / previousClose) * 100;
            String movementPercentage = String.format( "%.2f", calcPercentage );
            stock.setMovementPercentage((isPositive ? "+" : "") + movementPercentage + "%");

            System.out.println("updated stock:" + stock);
            stockRepository.save(stock);
        }

    }
}
