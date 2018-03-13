package com.zakshya.zakchange.marketdata.infrastructure.domain.services;

import com.zakshya.zakchange.commons.market.entities.TickerInfo;
import com.zakshya.zakchange.commons.market.exceptions.InvalidCurrencyPairException;
import com.zakshya.zakchange.commons.market.exceptions.InvalidExchangeException;
import com.zakshya.zakchange.commons.validation.ValidationResult;
import com.zakshya.zakchange.commons.validation.ValidationStatus;
import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;

import java.util.Set;

public class MarketDataService {

    private final MarketDataProvider marketDataProvider;
    private final MarketDataValidator marketDataValidator;

    public MarketDataService(MarketDataProvider marketDataProvider, MarketDataValidator marketDataValidator) {
        this.marketDataProvider = marketDataProvider;
        this.marketDataValidator = marketDataValidator;
    }

    public Set<TickerInfo> getLatestPrices(Set<String> from, Set<String> to) {
        ValidationResult validation = marketDataValidator.validateFromToCurrencyPairs(from, to);
        if (ValidationStatus.ERROR.equals(validation.getStatus())) {
            throw new InvalidCurrencyPairException(validation.getReasonsAsString());
        }
        return marketDataProvider.getLatestPrices(from, to);

    }

    public Set<TickerInfo> getLatestPrices(Set<String> from, Set<String> to, String exchange) {
        ValidationResult validation = marketDataValidator.validateExchange(exchange);
        if (ValidationStatus.ERROR.equals(validation.getStatus())) {
            throw new InvalidExchangeException(validation.getReasonsAsString());
        }

        validation = marketDataValidator.validateFromToCurrencyPairsForExchange(from, to, exchange);
        if (ValidationStatus.ERROR.equals(validation.getStatus())) {
            throw new InvalidCurrencyPairException(validation.getReasonsAsString());
        }

        return marketDataProvider.getLatestPrices(from, to, exchange);

    }

    public Set<String> getSupportedExchanges() {
        return  marketDataProvider.getSupportedExchanges();
    }
}
