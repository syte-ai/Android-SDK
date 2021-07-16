package com.syte.ai.android_sdk.data;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class CropCoordinates {

    private double mX1 = 0;
    private double mY1 = 0;
    private double mX2 = 1;
    private double mY2 = 1;

    public CropCoordinates(double x1, double y1, double x2, double y2) {
        mX1 = x1;
        mX2 = x2;
        mY1 = y1;
        mY2 = y2;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "{\"x\":" +
                mX1 +
                ",\"y\":" +
                mY1 +
                ",\"x2\":" +
                mX2 +
                ",\"y2\":" +
                mY2 +
                "}";
    }
}
