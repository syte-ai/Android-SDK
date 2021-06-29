package com.syte.ai.android_sdk.data;

import android.net.Uri;

import java.util.Arrays;
import java.util.Objects;

public class ImageSearchRequestData {

    private final Uri mImageUri;
    private Double[] mCoordinates;
    private boolean mRetrieveOffersForTheFirstBound = true;

    public ImageSearchRequestData(Uri imageUri) {
        this.mImageUri = imageUri;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setCoordinates(Double[] coordinates) {
        this.mCoordinates = coordinates;
    }

    public void setRetrieveOffersForTheFirstBound(boolean retrieveOffersForTheFirstBound) {
        mRetrieveOffersForTheFirstBound = retrieveOffersForTheFirstBound;
    }

    public boolean isRetrieveOffersForTheFirstBound() {
        return mRetrieveOffersForTheFirstBound;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageSearchRequestData that = (ImageSearchRequestData) o;
        return mImageUri.equals(that.mImageUri) &&
                Arrays.equals(mCoordinates, that.mCoordinates);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mImageUri);
        result = 31 * result + Arrays.hashCode(mCoordinates);
        return result;
    }

}