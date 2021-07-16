package com.syte.ai.android_sdk.enums;

public enum Catalog {
    GENERAL("general"),
    HOME("home"),
    FASHION("fashion");

    private final String mName;

    Catalog(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
