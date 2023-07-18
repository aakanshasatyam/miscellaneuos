package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;
import com.limitorders.verifier.entity.Trade;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LimitOrderProcessor {
    private static final double EPSILON = 1e-6;

    private PriorityQueue<Stock> buyLimitOrders;
    private PriorityQueue<Stock> sellLimitOrders;
    private List<Trade> tradeList;

    public LimitOrderProcessor() {
         buyLimitOrders = new PriorityQueue<>((a, b) -> {
            if (b.getPrice() == (a.getPrice())) {
                return a.getLocalDateTime().compareTo(b.getLocalDateTime());
            } else {
                return Double.compare(b.getPrice(), a.getPrice());
            }
        }
        );

        sellLimitOrders = new PriorityQueue<>((a, b) -> {
            if (Math.abs(a.getPrice() - b.getPrice()) < EPSILON) {
                return a.getLocalDateTime().compareTo(b.getLocalDateTime());
            } else {
                return Double.compare(a.getPrice(), b.getPrice());
            }
        });

        tradeList = new ArrayList<>();
    }

    public LimitOrderProcessor(PriorityQueue<Stock>buyLimitOrders, PriorityQueue<Stock>sellLimitOrders) {
        this.buyLimitOrders = buyLimitOrders;
        this.sellLimitOrders = sellLimitOrders;
    }

    public void setLimitOrders(Stock stock, PriorityQueue<Stock>buyLimitOrders, PriorityQueue<Stock>sellLimitOrders) throws Exception {
        switch (stock.getSide()) {
            case 'B': {
                buyLimitOrders.add(stock);
                break;
            }
            case 'S': {
                sellLimitOrders.add(stock);
                break;
            }
            default:
                throw new Exception();
        }
    }

    private void processBuyOrders(Stock currentLimitOrder) {
        while (sellLimitOrders.size() > 0 && (currentLimitOrder.getPrice() >= sellLimitOrders.peek().getPrice())) {

            Stock sellLimitOrder = sellLimitOrders.poll();
            long aggressingOrderId = currentLimitOrder.getOrderId();
            long restingOrderId = sellLimitOrder.getOrderId();

            long remainingQuantity = sellLimitOrder.getQuantity() - currentLimitOrder.getQuantity();

            if (remainingQuantity > 0) {

                Trade trade = new Trade(aggressingOrderId, restingOrderId, sellLimitOrder.getPrice(), currentLimitOrder.getQuantity());
                tradeList.add(trade);
                sellLimitOrder.setQuantity(remainingQuantity);
                sellLimitOrders.add(sellLimitOrder);
                break;

            } else{
                currentLimitOrder.setQuantity(-1*remainingQuantity);
                Trade trade = new Trade(aggressingOrderId, restingOrderId, sellLimitOrder.getPrice(), sellLimitOrder.getQuantity());
                tradeList.add( trade);
            }
        }

        if(currentLimitOrder.getQuantity() > 0) {
            buyLimitOrders.add(currentLimitOrder);
        }
    }

    private void processSellOrders(Stock currentLimitOrder) {
        while (buyLimitOrders.size() > 0 && (currentLimitOrder.getPrice() <= buyLimitOrders.peek().getPrice())) {

            Stock buyLimitOrder = buyLimitOrders.poll();
            long aggressingOrderId = currentLimitOrder.getOrderId();
            long restingOrderId = buyLimitOrder.getOrderId();

            long remainingQuantity = buyLimitOrder.getQuantity() - currentLimitOrder.getQuantity();

            if (remainingQuantity > 0) {

                Trade trade = new Trade(aggressingOrderId, restingOrderId, currentLimitOrder.getPrice(), currentLimitOrder.getQuantity());
                tradeList.add(trade);
                buyLimitOrder.setQuantity(remainingQuantity);
                buyLimitOrders.add(buyLimitOrder);
                break;

            } else{
                currentLimitOrder.setQuantity(-1*remainingQuantity);
                Trade trade = new Trade(aggressingOrderId, restingOrderId, currentLimitOrder.getPrice(), buyLimitOrder.getQuantity());
                tradeList.add( trade);
            }
        }

        if(currentLimitOrder.getQuantity() > 0) {
            sellLimitOrders.add(currentLimitOrder);
        }
    }

    public void findTradedOrders(Stock currentLimitOrder) {

        if (currentLimitOrder.getSide() == 'B') {
            processBuyOrders(currentLimitOrder);
        } else if (currentLimitOrder.getSide() == 'S') {
            processSellOrders(currentLimitOrder);
        }
    }

    public void printTradedOrders() {
        // print the output
        for (Trade trade : tradeList) {
            System.out.println("traded " + trade.getAggressingOrderId() + " "
                    + trade.getRestingOrderId() + " "
                    + trade.getPrice() + " " +
                    trade.getQuantity());
        }
    }

    //limitOrderProcessorPriorityQueue

}
