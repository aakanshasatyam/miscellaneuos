package com.limitorders.verifier.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {
    private long aggressingOrderId;
    private long restingOrderId;
    private double price;
    private long quantity;

    public Trade(long aggressingOrderId, long restingOrderId, double price, long quantity) {
        this.aggressingOrderId = aggressingOrderId;
        this.restingOrderId = restingOrderId;
        this.price = price;
        this.quantity = quantity;
    }

}
