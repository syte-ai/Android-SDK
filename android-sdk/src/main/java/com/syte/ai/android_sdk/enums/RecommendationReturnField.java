package com.syte.ai.android_sdk.enums;

public enum RecommendationReturnField {

    IMAGE_URL_AND_SKU("imageUrl,sku"),
    IMAGE_URL("imageUrl"),
    SKU("sku"),
    ALL("*");

    private final String mName;

    RecommendationReturnField(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
