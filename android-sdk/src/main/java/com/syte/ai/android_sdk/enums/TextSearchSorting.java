package com.syte.ai.android_sdk.enums;

public enum TextSearchSorting {

    DEFAULT(""), PRICE_ASC("price_asc"), PRICE_DESC("price_desc");

    private final String mName;

    TextSearchSorting(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
