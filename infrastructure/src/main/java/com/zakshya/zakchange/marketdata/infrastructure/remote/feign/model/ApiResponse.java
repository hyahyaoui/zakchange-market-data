package com.zakshya.zakchange.marketdata.infrastructure.remote.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponse<T> {

    @JsonProperty("Message")
    public String message;
    @JsonProperty("Response")
    public String response;
    @JsonProperty("Type")
    public int type;
    @JsonProperty("Data")
    public T data;
}
