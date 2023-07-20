package com.limitorders.verifier.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Stock {
    private long orderId;
    private char side;
    private double price;
    private long quantity;
    LocalDateTime localDateTime;

    public Stock(long orderId, char side, double price, long quantity, LocalDateTime dateTime) {
        this.orderId = orderId;
        this.side = side;
        this.price = price;
        this.quantity = quantity;
        this.localDateTime = dateTime;
    }
};