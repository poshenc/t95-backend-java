package com.t95.t95backend.dto;

import java.util.List;

public class YahooFinanceDTO {
    private Chart chart;
    public Chart getChart() {
        return chart;
    }

    public static class Chart {
        private List<Result> result;
        public List<Result> getResult() {
            return result;
        }
    }

    public static class Result {
        private Meta meta;
        public Meta getMeta() {
            return meta;
        }
    }

    public static class Meta {
        private String currency;
        private String symbol;
        private Double regularMarketPrice;
        private Double previousClose;

        public String getCurrency() {
            return currency;
        }
        public String getSymbol() {
            return symbol;
        }
        public Double getRegularMarketPrice() {
            return regularMarketPrice;
        }
        public Double getPreviousClose() {
            return previousClose;
        }
    }
}
