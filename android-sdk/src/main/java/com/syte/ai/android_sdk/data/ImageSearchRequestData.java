package com.syte.ai.android_sdk.data;

import java.util.Arrays;

public class ImageSearchRequestData {

    private final Byte[] mImagePayload;
    private Double[] mCoordinates;

    public ImageSearchRequestData(Byte[] imagePayload) {
        this.mImagePayload = imagePayload;
    }

    public void setCoordinates(Double[] coordinates) {
        this.mCoordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageSearchRequestData that = (ImageSearchRequestData) o;
        return Arrays.equals(mImagePayload, that.mImagePayload) &&
                Arrays.equals(mCoordinates, that.mCoordinates);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(mImagePayload);
        result = 31 * result + Arrays.hashCode(mCoordinates);
        return result;
    }

}