package com.zakshya.zakchange.marketdata.infrastructure.domain.services;

import com.zakshya.zakchange.commons.validation.ValidationResult;
import com.zakshya.zakchange.commons.validation.ValidationStatus;
import com.zakshya.zakchange.marketdata.infrastructure.domain.providers.MarketDataProvider;

import javax.validation.Valid;
import java.util.Set;

@Valid
public class MarketDataValidator {
    private final MarketDataProvider marketDataProvider;

    public MarketDataValidator(MarketDataProvider marketDataProvider) {
        this.marketDataProvider = marketDataProvider;
    }

    public ValidationResult validateFromToCurrencyPairs(Set<String> from, Set<String> to) {
        return validateFromToCurrencies(from, to, marketDataProvider.getSupportedCurrencies());
    }

    public ValidationResult validateExchange(String exchange) {
        ValidationResult validationResult = new ValidationResult(ValidationStatus.SUCCESS);
        if (!isExchangeSupported(exchange)) {
            validationResult.setStatus(ValidationStatus.ERROR);
            validationResult.getReasons()
                    .add(String.format("Exchange %s is not supported.", exchange));
        }
        return validationResult;
    }

    public ValidationResult validateFromToCurrencyPairsForExchange(Set<String> from, Set<String> to, String exchange) {
        return validateFromToCurrencies(from, to, marketDataProvider.getSupportedCurrencies(exchange));
    }

    private boolean isExchangeSupported(String exchange) {
        return marketDataProvider.getSupportedExchanges().contains(exchange);
    }

    private ValidationResult validateFromToCurrencies(Set<String> from, Set<String> to,
                                                      Set<String> supportedCurrencies) {
        ValidationResult validationResult = new ValidationResult(ValidationStatus.SUCCESS);
        if (!supportedCurrencies.containsAll(from)) {
            validationResult.setStatus(ValidationStatus.ERROR);

            from.removeAll(supportedCurrencies);
            String error = from.stream().reduce("The following currency/currencies in 'from' is/are not supported:",
                    (previous, current) -> previous.concat("\n" + current));

            validationResult.getReasons().add(error);

        }
        if (!supportedCurrencies.containsAll(to)) {
            validationResult.setStatus(ValidationStatus.ERROR);

            to.removeAll(supportedCurrencies);
            String error = to.stream().reduce("The following currency/currencies in 'to' is/are not supported:",
                    (previous, current) -> previous.concat("\n" + current));

            validationResult.getReasons().add(error);

        }

        return validationResult;
    }
}
