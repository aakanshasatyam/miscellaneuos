package com.limitorders.verifier.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trade {
    public long aggressingOrderId;
    public long restingOrderId;
    public double price;
    public long quantity;

    public Trade(long aggressingOrderId, long restingOrderId, double price, long quantity) {
        this.aggressingOrderId = aggressingOrderId;
        this.restingOrderId = restingOrderId;
        this.price = price;
        this.quantity = quantity;
    }

}
