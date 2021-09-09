package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.ArrayList;
import java.util.List;

/**
 * This event should be sent to Syte every time a user adds a products to cart
 */
public class EventProductsAddedToCart extends BaseSyteEvent {

    @SerializedName("products")
    private final List<Product> mProducts;

    /**
     * @param productList list of {@link Product}
     * @param pageName unique page name given by the app developer
     */
    public EventProductsAddedToCart(
            List<Product> productList,
            String pageName
    ) {
        super("products_added_to_cart", pageName, EventsTag.ECOMMERCE);
        mProducts = productList;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventProductsAddedToCart.class);
    }
}