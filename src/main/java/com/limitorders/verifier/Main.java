package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class Main {
    private static final double EPSILON = 1e-6;

    public static void main(String args[]) throws Exception {
        PriorityQueue<Stock> buyLimitOrders = new PriorityQueue<>((a, b) -> {
            if (b.getPrice() == (a.getPrice())) {
                return a.getLocalDateTime().compareTo(b.getLocalDateTime());
            } else {
                return Double.compare(b.getPrice(), a.getPrice());
            }
        }
        );

        PriorityQueue<Stock> sellLimitOrders = new PriorityQueue<>((a, b) -> {
            if (Math.abs(a.getPrice() - b.getPrice()) < EPSILON) {
                return a.getLocalDateTime().compareTo(b.getLocalDateTime());
            } else {
                return Double.compare(a.getPrice(), b.getPrice());
            }
        });

        LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor(buyLimitOrders, sellLimitOrders);
        limitOrderProcessor.setLimitOrders(new Stock(10000, 'B', 98, 25500, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);
        limitOrderProcessor.setLimitOrders(new Stock(10005, 'S', 105, 20000, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);
        limitOrderProcessor.setLimitOrders(new Stock(10001, 'S', 100, 500, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);
        limitOrderProcessor.setLimitOrders(new Stock(10002, 'S', 100, 10000, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);
        limitOrderProcessor.setLimitOrders(new Stock(10003, 'B', 99, 50000, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);
        limitOrderProcessor.setLimitOrders(new Stock(10004, 'S', 103, 100, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);
        limitOrderProcessor.findTradedOrders();
        limitOrderProcessor.setLimitOrders(new Stock(10006, 'B', 105, 16000, LocalDateTime.now()), limitOrderProcessor.buyLimitOrders, limitOrderProcessor.sellLimitOrders);


        limitOrderProcessor.findTradedOrders();
    }
}
