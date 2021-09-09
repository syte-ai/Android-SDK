package com.syte.ai.android_sdk.enums;

/**
 * Default Syte products types.
 */
public enum SyteProductType {
    CAMERA("camera"), DISCOVERY_BUTTON("discovery_button");

    public final String name;

    SyteProductType(String name) {
        this.name = name;
    }

}
