package com.example.muj.goldenfurniture.database;


public class Cart
{
    String uid,productBrand, productId,  productImage;
    Long productPrice;

    public Cart() {
    }

    public Cart(String uid, String productBrand, String productId, Long productPrice, String productImage) {
        this.uid = uid;
        this.productBrand = productBrand;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getUid() {
        return uid;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductId() {
        return productId;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
