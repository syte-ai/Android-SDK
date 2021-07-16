package com.syte.ai.android_sdk.enums;

public enum Placement {
    DEFAULT("default"),
    SEARCH_BAR("search-bar"),
    SIDE_MENU("side-menu");

    private String mName;

    Placement(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
