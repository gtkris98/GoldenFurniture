package com.example.muj.goldenfurniture.database;

import android.util.Log;

import java.util.Calendar;

public class orders
{
    String userId,deliveryAddress,productModel,status;
    int amount;
    Long unixTimeStamp;

    public orders() {
    }

    public orders(String userId, String deliveryAddress, String productModel, String status, int amount, Long unixTimeStamp) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.productModel = productModel;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public Long getUnixTimeStamp() {
        return unixTimeStamp;
    }

    public int getAmount() {
        return amount;
    }
}
