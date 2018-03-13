package com.zakshya.zakchange.marketdata.infrastructure.remote.feign.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class SupportedExchanges extends HashMap<String, CurrencyPair> {
}
