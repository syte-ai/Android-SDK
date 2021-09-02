package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.List;

/**
 * This event should be sent to Syte every time a user complete his checkout process
 */
public class EventCheckoutComplete extends BaseSyteEvent{

    @SerializedName("id")
    private String mId;

    @SerializedName("products")
    private final List<Product> mProducts;

    @SerializedName("value")
    private final double mValue;

    @SerializedName("currency")
    private final String mCurrency;

    /**
     * @param id unique id of transaction
     * @param value total cost of a cart for checkout including delivery fee
     * @param currency currency of checkout
     * @param productList array of product objects
     * @param pageName unique page name given by the app developer
     */
    public EventCheckoutComplete(
            String id,
            double value,
            String currency,
            List<Product> productList,
            String pageName) {
        super("checkout_complete", pageName, EventsTag.ECOMMERCE);
        mId = id;
        mProducts = productList;
        mValue = value;
        mCurrency = currency;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventCheckoutComplete.class);
    }
}
