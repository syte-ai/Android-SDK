package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Default Syte event
 */
public class EventProductsAddedToCart extends BaseSyteEvent {

    @SerializedName("products")
    private final List<Product> mProducts;

    public EventProductsAddedToCart(
            List<Product> productList,
            String syteUrlReferer
    ) {
        super("products_added_to_cart", syteUrlReferer, EventsTag.ECOMMERCE);
        mProducts = productList;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventProductsAddedToCart.class);
    }
}