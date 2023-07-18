package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;
import com.limitorders.verifier.entity.Trade;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LimitOrderProcessor {
    private static final double EPSILON = 1e-6;
    // available buy price is greater than the max sell price then it will be executed.
    // if sell orders are of higher price than anyone is ready to buy at then it will wait.

    // OrderId, Type, quantity, price.
    // if the price is same, then the orders should be executed based on the time of arrival,
    // PriorityQueue<
    // max priority queue for buy orders as we
    // min priority queue for sell orders.

    public PriorityQueue<Stock> buyLimitOrders;
    public PriorityQueue<Stock> sellLimitOrders;

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

    public void findTradedOrders() {

        PriorityQueue<Pair<Integer, Trade>> tradedList = new PriorityQueue<>(Comparator.comparingInt(Pair::getKey));
        int count = 0;
        while (buyLimitOrders.size() > 0 && sellLimitOrders.size() > 0 && (buyLimitOrders.peek().getPrice() >= sellLimitOrders.peek().getPrice())) {

            Stock buyLimitOrder = buyLimitOrders.poll();
            Stock sellLimitOrder = sellLimitOrders.poll();
            long aggressingOrderId = 0;
            long restingOrderId = 0;
            if (buyLimitOrder.getLocalDateTime().isAfter(sellLimitOrder.getLocalDateTime())) {
                aggressingOrderId = buyLimitOrder.getOrderId();
                restingOrderId = sellLimitOrder.getOrderId();
            } else {
                aggressingOrderId = sellLimitOrder.getOrderId();
                restingOrderId = buyLimitOrder.getOrderId();
            }
            long remainingQuantity = sellLimitOrder.getQuantity() - buyLimitOrder.getQuantity();

            if (remainingQuantity > 0) {


                sellLimitOrders.add(sellLimitOrder);
                Trade trade = new Trade(aggressingOrderId, restingOrderId, sellLimitOrder.getPrice(), buyLimitOrder.getQuantity());
                tradedList.add(new Pair<>(count++, trade));
                sellLimitOrder.setQuantity(remainingQuantity);

            } else if (remainingQuantity < 0) {
                buyLimitOrders.add(buyLimitOrder);
                Trade trade = new Trade(aggressingOrderId, restingOrderId, sellLimitOrder.getPrice(), sellLimitOrder.getQuantity());
                tradedList.add(new Pair<>(count++, trade));
                buyLimitOrder.setQuantity(-1 * remainingQuantity);
            } else {
                Trade trade = new Trade(aggressingOrderId, restingOrderId, sellLimitOrder.getPrice(), sellLimitOrder.getQuantity());
                tradedList.add(new Pair<>(count++, trade));
            }

        }

        // print the output

        while (!tradedList.isEmpty()) {
            System.out.println("traded " + tradedList.peek().getValue().getAggressingOrderId() + " "
                    + tradedList.peek().getValue().getRestingOrderId() + " "
                    + tradedList.peek().getValue().getPrice() + " " +
                    tradedList.peek().getValue().getQuantity());
            tradedList.poll();
        }

    }


    //limitOrderProcessorPriorityQueue

}
