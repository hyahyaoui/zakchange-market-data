package com.zakshya.zakchange.marketdata.infrastructure.domain.providers;

import com.zakshya.zakchange.commons.entities.TickerInfo;

import java.util.Set;

public interface MarketDataProvider {
    Set<TickerInfo> getLatestPrices(Set<String> from, Set<String> to, String exchange);
    Set<String> getSupportedCurrencies(String exchange);
    Set<String> getSupportedExchanges();
}
