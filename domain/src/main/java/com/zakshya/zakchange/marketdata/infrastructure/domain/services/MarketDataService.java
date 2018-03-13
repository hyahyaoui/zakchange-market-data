package com.zakshya.zakchange.marketdata.infrastructure.domain.services;

import com.zakshya.zakchange.commons.entities.TickerInfo;
import com.zakshya.zakchange.commons.exceptions.UnsupportedExchangeException;
import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MarketDataService {

    private final MarketDataProvider marketDataProvider;

    public MarketDataService(MarketDataProvider marketDataProvider) {
        this.marketDataProvider = marketDataProvider;
    }

    public Set<TickerInfo> getLatestPrices(Set<String> from, Set<String> to, String exchange) {
        validateRequest(from, to, exchange);
        return marketDataProvider.getLatestPrices(from, to, exchange);

    }

    private void validateRequest(Set<String> from, Set<String> to, String exchange) {

        if (exchange != null) {
            validateExchange(exchange);
        }

    }

    private void validateExchange(String exchange) {
        Set<String> supportedExchanges = this.marketDataProvider.getSupportedExchanges()
                .stream()
                .map(String::toLowerCase)
                .collect(toSet());
        if (supportedExchanges.contains(exchange.toLowerCase())) {
            throw new UnsupportedExchangeException("Exchange " + exchange + " is not supported");
        }
    }
}
