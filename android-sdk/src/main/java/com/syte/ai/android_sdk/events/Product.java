package com.syte.ai.android_sdk.events;

public class Product {

    private String sku;
    private int quantity;
    private double price;

    public Product(String sku, int quantity, double price) {
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

}
