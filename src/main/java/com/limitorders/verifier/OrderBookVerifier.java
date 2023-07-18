package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.Scanner;

public class OrderBookVerifier {
    private static final double EPSILON = 1e-6;

    public static void main(String args[]) throws Exception {

        LimitOrderProcessor limitOrderProcessor = new LimitOrderProcessor();

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
            limitOrderProcessor.printTradedOrders();
        }


        limitOrderProcessor.printTradedOrders();
    }
}
