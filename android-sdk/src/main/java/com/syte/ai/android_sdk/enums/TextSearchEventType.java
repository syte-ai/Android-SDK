package com.syte.ai.android_sdk.enums;

public enum TextSearchEventType {

    SUBMIT("submit"),
    POPULAR_SEARCH("popular_search"),
    RECENT_SEARCH("recent_search");

    private final String mName;

    TextSearchEventType(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
