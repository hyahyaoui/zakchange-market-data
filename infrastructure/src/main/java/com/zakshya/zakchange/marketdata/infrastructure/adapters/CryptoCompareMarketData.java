package com.zakshya.zakchange.marketdata.infrastructure.adapters;

import com.zakshya.zakchange.commons.market.entities.TickerInfo;
import com.zakshya.zakchange.marketdata.infrastructure.adapters.mappers.TickerInfoMapper;
import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;
import com.zakshya.zakchange.marketdata.infrastructure.remote.feign.CryptoCompare;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;

public class CryptoCompareMarketData implements MarketDataProvider {

    private final CryptoCompare cryptoCompare;

    public CryptoCompareMarketData(CryptoCompare cryptoCompare) {
        this.cryptoCompare = cryptoCompare;
    }

    @Override
    public Set<TickerInfo> getLatestPrices(Set<String> from, Set<String> to) {
        return getLatestPrices(from, to, null);
    }

    @Override
    public Set<TickerInfo> getLatestPrices(Set<String> from, Set<String> to, String exchange) {
        return this.cryptoCompare.getLatestPrices(arrayToCommaDelimitedString(from.toArray()),
                arrayToCommaDelimitedString(to.toArray()), exchange).getTickers()
                .values()
                .stream()
                .flatMap(tickers -> tickers.values().stream())
                .map(TickerInfoMapper::from)
                .collect(Collectors.toSet());
    }

    @Cacheable
    @Override
    public Set<String> getSupportedCurrencies(String exchange) {
        return this.cryptoCompare.getSupportedCoins().getData().keySet();
    }

    @Cacheable
    @Override
    public Set<String> getSupportedCurrencies() {
        return this.cryptoCompare.getSupportedCoins().getData().keySet();
    }

    @Cacheable
    @Override
    public Set<String> getSupportedExchanges() {
        return this.cryptoCompare.getSupprtedExchanges().getData().keySet();
    }
}
