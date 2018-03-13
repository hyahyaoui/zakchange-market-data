package com.zakshya.zakchange.marketdata.infrastructure.domain.services;

import com.zakshya.zakchange.commons.entities.CurrencyPair;
import com.zakshya.zakchange.commons.entities.TickerInfo;
import com.zakshya.zakchange.commons.exceptions.UnsupportedCurrencyPairException;
import com.zakshya.zakchange.commons.exceptions.UnsupportedExchangeException;
import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Stream;

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
        validateCurrencyPairs(from, to);

    }

    private void validateCurrencyPairs(Set<String> bases, Set<String> counters) {
       Set<CurrencyPair> pairs =  bases.stream()
                .flatMap(base -> counters.stream().map(counter -> CurrencyPair.builder().base(base).counter(counter).build()))
                .collect(toSet());
       pairs.removeAll(marketDataProvider.getSupportedCurrencyPairs(""));
       if(!pairs.isEmpty()) {
           String unsupported = pairs.stream()
                   .map(CurrencyPair::toString)
                   .reduce("", (ov,nv)-> ov.concat(",").concat(nv));
           throw  new UnsupportedCurrencyPairException("Currency pair(s) ["+unsupported+"] is/are not supported");
       }

    }

    private void validateExchange(String exchange) {
        Set<String> supportedExchanges = this.marketDataProvider.getSupportedExchanges()
                .stream()
                .map(String::toLowerCase)
                .collect(toSet());
        if (supportedExchanges.contains(exchange.toLowerCase())) {
            throw new UnsupportedExchangeException("Exchange [" + exchange + "] is not supported");
        }
    }
}
