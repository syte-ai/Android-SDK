package com.syte.ai.android_sdk.events;

/**
 * Product passed to some of the default events.
 */
public class Product {

    private String sku;
    private int quantity;
    private double price;

    /**
     *
     * @param sku
     * @param quantity
     * @param price
     */
    public Product(String sku, int quantity, double price) {
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * @return product ID
     */
    public String getSku() {
        return sku;
    }

    /**
     * @return product quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return product price
     */
    public double getPrice() {
        return price;
    }

}
