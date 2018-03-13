package com.zakshya.zakchange.marketdata.infrastructure.remote.feign.model;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class CurrencyPair {
    private Map<String, Set<String>> pairs;
}
