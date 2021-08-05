package com.syte.ai.android_sdk.enums;

import com.syte.ai.android_sdk.data.result.offers.Item;

/**
 * Values are used in the Recommendation requests (Similar products, Shop the look, Personalization)
 */
public enum RecommendationReturnField {

    /**
     * Return only image URL and product ID (SKU). All other fields in {@link Item} will be null!
     */
    IMAGE_URL_AND_SKU("imageUrl,sku"),
    /**
     * Return only image URL. All other fields in {@link Item} will be null!
     */
    IMAGE_URL("imageUrl"),
    /**
     * Return only product ID (SKU). All other fields in {@link Item} will be null!
     */
    SKU("sku"),
    /**
     * Return all fields.
     */
    ALL("*");

    private final String mName;

    RecommendationReturnField(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
