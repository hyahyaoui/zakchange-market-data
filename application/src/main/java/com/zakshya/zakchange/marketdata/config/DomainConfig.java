package com.zakshya.zakchange.marketdata.config;

import com.zakshya.zakchange.marketdata.infrastructure.adapters.CryptoCompareMarketData;
import com.zakshya.zakchange.marketdata.infrastructure.domain.services.MarketDataService;
import com.zakshya.zakchange.marketdata.infrastructure.remote.feign.CryptoCompare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackageClasses = {CryptoCompare.class})
@Configuration
public class DomainConfig {

    @Bean
    @Autowired
    public CryptoCompareMarketData cryptoCompareMarketData(CryptoCompare cryptoCompare) {
        return new CryptoCompareMarketData(cryptoCompare);
    }

    @Bean
    @Autowired
    public MarketDataService marketDataService(CryptoCompareMarketData cryptoCompareMarketData) {
        return new MarketDataService(cryptoCompareMarketData);
    }
}
