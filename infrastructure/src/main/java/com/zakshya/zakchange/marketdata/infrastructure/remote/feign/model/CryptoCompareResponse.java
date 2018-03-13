package com.zakshya.zakchange.marketdata.infrastructure.remote.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CryptoCompareResponse<T> {

    @JsonProperty("Response")
    private String response;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Data")
    private T data;

}
