package com.example.muj.goldenfurniture.database;

public class orders
{
    String userId,deliveryAddress,productModel;
    int amount;

    public orders() {
    }

    public orders(String userId, String deliveryAddress, String productModel, int amount) {
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.productModel = productModel;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }
}
