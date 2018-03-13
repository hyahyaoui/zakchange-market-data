package com.zakshya.zakchange.marketdata.infrastructure.rest;

import com.zakshya.zakchange.commons.entities.TickerInfo;
import com.zakshya.zakchange.marketdata.infrastructure.domain.services.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/market-data")
public class MarketDataController {

    private final MarketDataService marketDataService;

    @Autowired
    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @RequestMapping(value = "/ticker", method = RequestMethod.GET)
    public ResponseEntity<Map<String, TickerInfo>> getTicker(@RequestParam("from") Set<String> from,
                                                             @RequestParam("to") Set<String> to,
                                                             @RequestParam(value = "exchange", required = false) String exchange) {
        return ResponseEntity.ok(marketDataService.getLatestPrices(from, to, exchange)
                .stream()
                .collect(Collectors.toMap(ticker -> ticker.getBase().concat("/").concat(ticker.getCounter())
                        , ticker -> ticker)));

    }
}
