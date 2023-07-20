package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;
import com.limitorders.verifier.entity.Trade;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This class is responsible for processing limit orders after each submission.
 * <p>
 * Assumptions/clarifications
 * 1. After each input we are processing the current order for the resting orders in out tradebook.
 * 2. We are printing the tradebook as well as the tradedorders if any at the time of submission of our orders.
 * 3. The same code can be modified to print both the tradebook as well as the traded orders just in the end if needed.
 * 4. I am taking the price as double in the input which should be the case practically, but I don't see the expected output
 * handling the same as double hence, I am changign that to int while printing the output as it is not specified how to
 * handle double in the output format.
 * 5. The traded price is always the sell price.
 */

public class LimitOrderProcessor {
    private static final double EPSILON = 1e-6;
    private PriorityQueue<Stock> buyLimitOrders;

    public PriorityQueue<Stock> getBuyLimitOrders() {
        return buyLimitOrders;
    }

    public PriorityQueue<Stock> getSellLimitOrders() {
        return sellLimitOrders;
    }

    public List<Trade> getTradeList() {
        return tradeList;
    }

    private PriorityQueue<Stock> sellLimitOrders;
    private List<Trade> tradeList;
    public OrderBookPrinter orderBookPrinter;

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
        orderBookPrinter = new OrderBookPrinter();
    }


    public void findTradedOrders(Stock currentLimitOrder) {

        if (currentLimitOrder.getSide() == 'B') {
            processBuyOrders(currentLimitOrder);
        } else if (currentLimitOrder.getSide() == 'S') {
            processSellOrders(currentLimitOrder);
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
                currentLimitOrder.setQuantity(0);
                break;

            } else {
                currentLimitOrder.setQuantity(-1 * remainingQuantity);
                Trade trade = new Trade(aggressingOrderId, restingOrderId, sellLimitOrder.getPrice(), sellLimitOrder.getQuantity());
                tradeList.add(trade);
            }
        }

        if (currentLimitOrder.getQuantity() > 0) {
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
                currentLimitOrder.setQuantity(0);
                break;

            } else {
                currentLimitOrder.setQuantity(-1 * remainingQuantity);
                Trade trade = new Trade(aggressingOrderId, restingOrderId, currentLimitOrder.getPrice(), buyLimitOrder.getQuantity());
                tradeList.add(trade);
            }
        }

        if (currentLimitOrder.getQuantity() > 0) {
            sellLimitOrders.add(currentLimitOrder);
        }
    }

}
