package com.syte.ai.android_sdk.data;

import java.util.Arrays;

public class ImageSearchRequestData {

    public Byte[] imagePayload;
    public Double[] coordinates;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageSearchRequestData that = (ImageSearchRequestData) o;
        return Arrays.equals(imagePayload, that.imagePayload) &&
                Arrays.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(imagePayload);
        result = 31 * result + Arrays.hashCode(coordinates);
        return result;
    }

}