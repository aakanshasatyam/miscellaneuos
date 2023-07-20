package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LimitOrderProcessorTest {

    @Test
    public void testOrderBookVerifierWithNoTradedOrder() {
        Stock buyOrder1 = new Stock(10000, 'B', 98, 25500, LocalDateTime.now());
        Stock sellOrder1 = new Stock(10001, 'S', 100, 1000, LocalDateTime.now());

        LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor();
        limitOrderProcessor.findTradedOrders(buyOrder1);
        limitOrderProcessor.findTradedOrders(sellOrder1);

        Assertions.assertEquals(limitOrderProcessor.getTradeList().size(), 0);
    }

    @Test
        public void testOrderBookVerifierWithOneTradedOrder() {
            Stock buyOrder1 = new Stock(10000, 'B', 98, 25500, LocalDateTime.now());
            Stock sellOrder1 = new Stock(10001, 'S', 95, 1000, LocalDateTime.now());

            LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor();
            limitOrderProcessor.findTradedOrders(buyOrder1);
            limitOrderProcessor.findTradedOrders(sellOrder1);

            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getQuantity(), 1000);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getPrice(), 95);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getAggressingOrderId(), 10000);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getRestingOrderId(), 10001);
        }

        @Test
        public void testOrderBookVerifierWithMultipleTradedOrders() {
            Stock buyOrder1 = new Stock(10000, 'B', 98, 25500, LocalDateTime.now());
            Stock sellOrder1 = new Stock(10001, 'S', 100, 1000, LocalDateTime.now());
            Stock buyOrder2 = new Stock(10002, 'B', 101, 1000, LocalDateTime.now());
            Stock sellOrder2 = new Stock(10003, 'S', 95, 1000, LocalDateTime.now());

            LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor();
            limitOrderProcessor.findTradedOrders(buyOrder1);
            limitOrderProcessor.findTradedOrders(sellOrder1);
            limitOrderProcessor.findTradedOrders(buyOrder2);
            limitOrderProcessor.findTradedOrders(sellOrder2);

            Assertions.assertEquals(limitOrderProcessor.getTradeList().size(), 2);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getQuantity(), 1000);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getPrice(), 100);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getAggressingOrderId(), 10002);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(0).getRestingOrderId(), 10001);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(1).getQuantity(), 1000);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(1).getPrice(), 95);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(1).getAggressingOrderId(), 10003);
            Assertions.assertEquals(limitOrderProcessor.getTradeList().get(1).getRestingOrderId(), 10000);
        }

}
