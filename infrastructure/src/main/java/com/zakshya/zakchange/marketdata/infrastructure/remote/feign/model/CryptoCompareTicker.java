package com.zakshya.zakchange.marketdata.infrastructure.remote.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CryptoCompareTicker {
    @JsonProperty("MARKET")
    private String market;
    @JsonProperty("FROMSYMBOL")
    private String fromSymbol;
    @JsonProperty("TOSYMBOL")
    private String toSymbol;
    @JsonProperty("PRICE")
    private double price;
    @JsonProperty("OPENDAY")
    private double openDay;
    @JsonProperty("HIGHDAY")
    private double highDay;
    @JsonProperty("LOWDAY")
    private double lowDay;
    @JsonProperty("OPEN24HOUR")
    private double open24Hour;
    @JsonProperty("HIGH24HOUR")
    private double high24Hour;
    @JsonProperty("LOW24HOUR")
    private double low24Hour;
    @JsonProperty("CHANGE24HOUR")
    private double change24Hour;
    @JsonProperty("CHANGEPCT24HOUR")
    private double changePct24hour;
    @JsonProperty("CHANGEPCTDAY")
    private double changePctDay;

}