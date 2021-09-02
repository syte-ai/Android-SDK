package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.List;

/**
 * This event should be sent to Syte every time a user starts a checkout process
 */
public class EventCheckoutStart extends BaseSyteEvent {

    @SerializedName("products")
    private final List<Product> mProducts;

    @SerializedName("value")
    private final double mValue;

    @SerializedName("currency")
    private final String mCurrency;

    /**
     * @param price total cost of a cart for checkout including delivery fee
     * @param currency currency of checkout
     * @param productList array of product objects
     * @param pageName unique page name given by the app developer
     */
    public EventCheckoutStart(
            double price,
            String currency,
            List<Product> productList,
            String pageName) {
        super("checkout_start", pageName, EventsTag.ECOMMERCE);
        mProducts = productList;
        mValue = price;
        mCurrency = currency;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventCheckoutStart.class);
    }

}
