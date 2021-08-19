package com.syte.ai.android_sdk.enums;

/**
 * Default tags for Syte events.
 */
public enum EventsTag {
    CAMERA("camera"),
    ECOMMERCE("ecommerce"),
    DISCOVERY_BUTTON("discovery_button"),
    SIMILAR_ITEMS("similar_items"),
    SYTE_ANDROID_SDK("syte_android_sdk"),
    TEXT_SEARCH("text_search"),
    SHOP_THE_LOOK("shop_the_look");

    private String mName;

    EventsTag(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
