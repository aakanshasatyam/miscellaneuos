package com.limitorders.verifier;

import com.limitorders.verifier.entity.Trade;
import java.util.List;

public class TradedStocksPrinter {
    private static final String TRADED_STRING_APPENDER = "traded ";

    public void printTradedOrders(List<Trade> tradeList) {
        for (Trade trade : tradeList) {
            StringBuilder sb = new StringBuilder();
            sb.append(TRADED_STRING_APPENDER).append(trade.getAggressingOrderId()).append(" ")
                    .append(trade.getRestingOrderId()).append(" ")
                    .append(trade.getPrice()).append(" ")
                    .append(trade.getQuantity());
            System.out.println(sb.toString());
        }
    }

}
