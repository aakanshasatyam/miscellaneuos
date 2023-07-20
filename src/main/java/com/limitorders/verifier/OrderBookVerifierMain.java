package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This is the main class for stock verifier project.
 * The input should be given in the order of orderid, side(B/S), price, quantity.
 * cmd + D will help you to submit the orders and get out of the loop.
 *
 *
 * Please Note: Once exited from the verifier new inputs will have to be submitted the old ones won't be retained.
 * For the orders to be retained we have to modify the implementation to not create new object and use the existing
 * object.
 */
public class OrderBookVerifierMain {

    public static void main(String args[]) {

        LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor();
        TradedStocksPrinter tradedStocksPrinter = new TradedStocksPrinter();
        OrderBookPrinter orderBookPrinter = new OrderBookPrinter();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            long orderId = Long.parseLong(parts[0]);
            char side = parts[1].charAt(0);
            double price = Double.parseDouble(parts[2]);
            long quantity = Long.parseLong(parts[3]);
            Stock stock = new Stock(orderId, side, price, quantity, LocalDateTime.now());
            limitOrderProcessor.findTradedOrders(stock);

            // Depending on the output criteria one can remove these and just print the final output.
            System.out.println("printing traded Orders");
            tradedStocksPrinter.printTradedOrders(limitOrderProcessor.getTradeList());
            System.out.println("printing order book");
            orderBookPrinter.printOrderBook(new PriorityQueue<>(limitOrderProcessor.getBuyLimitOrders()), new PriorityQueue<>(limitOrderProcessor.getSellLimitOrders()));
        }

        orderBookPrinter.printOrderBook(limitOrderProcessor.getBuyLimitOrders(), limitOrderProcessor.getSellLimitOrders());
        tradedStocksPrinter.printTradedOrders(limitOrderProcessor.getTradeList());
    }
}
