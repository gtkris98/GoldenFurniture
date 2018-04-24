package com.example.muj.goldenfurniture.database;

import java.util.Calendar;

public class orders
{
    String userId,deliveryAddress,productModel;
    int amount;
    long unixTimeStamp;

    public orders() {
    }

    public orders(String userId, String deliveryAddress, String productModel, int amount, long unixTimeStamp) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.productModel = productModel;
        this.amount = amount;
        this.unixTimeStamp = unixTimeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getProductModel() {
        return productModel;
    }

    public long getUnixTimeStamp() {
        return unixTimeStamp;
    }

    public int getAmount() {
        return amount;
    }
}
