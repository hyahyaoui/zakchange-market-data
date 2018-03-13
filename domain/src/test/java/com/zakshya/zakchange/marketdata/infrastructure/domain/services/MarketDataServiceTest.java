package com.zakshya.zakchange.marketdata.infrastructure.domain.services;

import com.zakshya.zakchange.commons.market.exceptions.InvalidCurrencyPairException;
import com.zakshya.zakchange.commons.market.exceptions.InvalidExchangeException;
import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

public class MarketDataServiceTest {

    private MarketDataProvider marketDataProvider = Mockito.mock(MarketDataProvider.class);
    private MarketDataValidator marketDataValidator = new MarketDataValidator(marketDataProvider);
    private MarketDataService marketDataService = new MarketDataService(marketDataProvider, marketDataValidator);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Set<String> supportedExchanges = new HashSet<>();
        supportedExchanges.add("kraken");
        supportedExchanges.add("coinbase");

        Set<String> supportedCurrencies = new HashSet<>();
        supportedCurrencies.add("BTC");
        supportedCurrencies.add("ETH");

        when(marketDataProvider.getSupportedExchanges()).thenReturn(supportedExchanges);
        when(marketDataProvider.getSupportedCurrencies()).thenReturn(supportedCurrencies);
    }

    @Test
    public void should_throw_unsupported_exchange_exception_when_getting_price_from_unsupported_exchange() {
        Set<String> from = new HashSet<>();
        from.add("BTC");
        Set<String> to = new HashSet<>();
        to.add("ETH");

        expectedException.expect(InvalidExchangeException.class);
        expectedException.expectMessage("Exchange unsupportedExchange is not supported.");

        marketDataService.getLatestPrices(from, to, "unsupportedExchange");
    }

    @Test
    public void should_throw_unsupported_currency_exception_when_getting_price_with_an_unsupported_from__currency() {
        Set<String> from = new HashSet<>();
        from.add("BTC");
        from.add("Invalid from");
        Set<String> to = new HashSet<>();
        to.add("ETH");

        expectedException.expect(InvalidCurrencyPairException.class);
        expectedException.expectMessage(
                "The following currency/currencies in 'from' is/are not supported:\nInvalid from");

        marketDataService.getLatestPrices(from, to);
    }

    @Test
    public void should_throw_unsupported_currency_exception_when_getting_price_with_an_unsupported_to_currency() {
        Set<String> from = new HashSet<>();
        from.add("ETH");
        Set<String> to = new HashSet<>();
        to.add("BTC");
        to.add("Invalid from");

        expectedException.expect(InvalidCurrencyPairException.class);
        expectedException.expectMessage(
                "The following currency/currencies in 'to' is/are not supported:\nInvalid from");

        marketDataService.getLatestPrices(from, to);
    }

}