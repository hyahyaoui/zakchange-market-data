package com.zakshya.zakchange.marketdata.infrastructure.domain.services;

import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

public class MarketDataValidatorTest {

    private MarketDataProvider marketDataProvider = Mockito.mock(MarketDataProvider.class);
    private MarketDataValidator marketDataValidator = new MarketDataValidator(marketDataProvider);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Set<String> supportedExchanges = new HashSet<>();
        supportedExchanges.add("kraken");
        supportedExchanges.add("coinbase");
        when(marketDataProvider.getSupportedExchanges()).thenReturn(supportedExchanges);
    }

    @Test
    public void should_throw_an_invalid_exchange_exception_when_trying_to_validate_an_non_existing_exchange() {
        marketDataValidator.validateExchange("invalidExchange");
    }
}