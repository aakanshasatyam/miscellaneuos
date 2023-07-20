package com.limitorders.verifier;

import com.limitorders.verifier.entity.Stock;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.PriorityQueue;

/**
 * This class prints the order book with buy orders in descending order and sell in ascending depending on their price.
 */
public class OrderBookPrinter {

    private static final int QUANTITY_SPACING = 11;
    private static final int PRICE_SPACING = 6;

    public void printOrderBook(PriorityQueue<Stock> buyOrderQueue, PriorityQueue<Stock> SellOrderQueue) {

        while (buyOrderQueue.size() > 0 || SellOrderQueue.size() > 0) {

            int buyPrice = (buyOrderQueue.size() == 0) ? (0) : (int) buyOrderQueue.peek().getPrice();
            long buyQuantity = (buyOrderQueue.size() == 0) ? (0) : buyOrderQueue.poll().getQuantity();
            int sellPrice = (SellOrderQueue.size() == 0) ? (0) : (int) SellOrderQueue.peek().getPrice();
            long sellQuantity = (SellOrderQueue.size() == 0) ? (0) : SellOrderQueue.poll().getQuantity();

            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            symbols.setGroupingSeparator(',');
            DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
            String buyQ = buyQuantity == 0 ? "" : decimalFormat.format(buyQuantity);

            String sellQ = sellQuantity == 0 ? "" : decimalFormat.format(sellQuantity);

            String buyOrderBookString = getFinalPaddedBuyString(buyQ, buyPrice);
            String sellOrderBookString = getFinalPaddedSellString(sellQ, sellPrice);

            System.out.println(buyOrderBookString + " | " + sellOrderBookString);

        }
    }

    private String getFinalPaddedBuyString(String buyQ, int buyPrice) {
        StringBuilder sb = new StringBuilder();
        String buyP = "";

        if (!buyQ.equals("")) {
            buyP = Integer.toString(buyPrice);
        }

        int paddedBuffer = QUANTITY_SPACING - buyQ.length();

        for (int i = 0; i < paddedBuffer; i++) {
            sb.append(" ");
        }
        sb.append(buyQ);
        sb.append(" ");

        paddedBuffer = PRICE_SPACING - buyP.length();

        for (int i = 0; i < paddedBuffer; i++) {
            sb.append(" ");
        }
        sb.append(buyP);

        return sb.toString();
    }

    private String getFinalPaddedSellString(String sellQ, int sellPrice) {
        StringBuilder sb = new StringBuilder();
        String sellP = "";

        if (!sellQ.equals("")) {
            sellP = Integer.toString(sellPrice);
        }
        int paddedBuffer = PRICE_SPACING - sellP.length();

        for (int i = 0; i < paddedBuffer; i++) {
            sb.append(" ");
        }
        sb.append(sellP);
        sb.append(" ");
        paddedBuffer = QUANTITY_SPACING - sellQ.length();

        for (int i = 0; i < paddedBuffer; i++) {
            sb.append(" ");
        }
        sb.append(sellQ);

        return sb.toString();
    }
}
