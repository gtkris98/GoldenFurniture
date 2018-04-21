package com.example.muj.goldenfurniture.database;

/**
 * Created by MUJ on 09-Mar-18.
 */

public class product
{
    String brand, category, image, model, size;
    long price;

    public product(String brand, String category, String image, String model, long price, String size) {
        this.brand = brand;
        this.category = category;
        this.image = image;
        this.model = model;
        this.price = price;
        this.size = size;
    }

    public product() {

    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getModel() {
        return model;
    }

    public long getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }
}
