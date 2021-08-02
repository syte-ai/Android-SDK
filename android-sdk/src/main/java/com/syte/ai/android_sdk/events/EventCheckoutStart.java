package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.List;

/**
 * Default Syte event
 */
public class EventCheckoutStart extends BaseSyteEvent {

    @SerializedName("products")
    private final List<Product> mProducts;

    @SerializedName("value")
    private final double mValue;

    @SerializedName("currency")
    private final String mCurrency;

    public EventCheckoutStart(
            double value,
            String currency,
            List<Product> productList,
            String syteUrlReferer) {
        super("checkout_start", syteUrlReferer, EventsTag.ECOMMERCE);
        mProducts = productList;
        mValue = value;
        mCurrency = currency;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventCheckoutStart.class);
    }

}
