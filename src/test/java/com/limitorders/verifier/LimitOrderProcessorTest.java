package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;

import java.time.LocalDateTime;

public class LimitOrderProcessorTest {

        public void testOrderBookVerifier() {
            Stock buyOrder1 = new Stock(10000, 'B', 98, 25500, LocalDateTime.now());
            Stock sellOrder1 = new Stock(10001, 'S', 95, 1000, LocalDateTime.now());

            LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor();
            limitOrderProcessor.findTradedOrders(buyOrder1);
            limitOrderProcessor.findTradedOrders(sellOrder1);

            //Assert.assertEquals(limitOrderProcessor.getTradeList().get(0).getQuantity(), 1000);
        }

}
