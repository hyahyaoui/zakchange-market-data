package com.zakshya.zakchange.marketdata.infrastructure.remote.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class MultiPriceResponse {

    @JsonProperty("RAW")
    private  Map<String, Map<String, CryptoCompareTicker>> tickers;
}
