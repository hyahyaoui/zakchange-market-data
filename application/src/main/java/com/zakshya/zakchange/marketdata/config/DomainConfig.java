package com.zakshya.zakchange.marketdata.config;

import com.zakshya.zakchange.marketdata.infrastructure.adapters.CryptoCompareMarketData;
import com.zakshya.zakchange.marketdata.infrastructure.domain.services.MarketDataService;
import com.zakshya.zakchange.marketdata.infrastructure.domain.services.MarketDataValidator;
import com.zakshya.zakchange.marketdata.infrastructure.remote.feign.CryptoCompare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author hyahyaoui
 */
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
    public MarketDataValidator marketDataValidator(CryptoCompareMarketData cryptoCompareMarketData) {

        return new MarketDataValidator(cryptoCompareMarketData);
    }

    @Bean
    @Autowired
    public MarketDataService marketDataService(CryptoCompareMarketData cryptoCompareMarketData, MarketDataValidator marketDataValidator) {

        return new MarketDataService(cryptoCompareMarketData, marketDataValidator);
    }
}
