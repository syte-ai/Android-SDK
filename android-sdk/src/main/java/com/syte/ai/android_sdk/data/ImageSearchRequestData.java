package com.syte.ai.android_sdk.data;

import android.net.Uri;

import java.util.Objects;

public class ImageSearchRequestData {

    private final Uri mImageUri;
    private CropCoordinates mCoordinates;
    private boolean mRetrieveOffersForTheFirstBound = true;
    private boolean mPersonalizedRanking = false;

    public ImageSearchRequestData(Uri imageUri) {
        this.mImageUri = imageUri;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setFirstBoundOffersCoordinates(CropCoordinates coordinates) {
        this.mCoordinates = coordinates;
    }

    public CropCoordinates getFirstBoundOffersCoordinates() {
        return mCoordinates;
    }

    public void setRetrieveOffersForTheFirstBound(boolean retrieveOffersForTheFirstBound) {
        mRetrieveOffersForTheFirstBound = retrieveOffersForTheFirstBound;
    }

    public boolean isRetrieveOffersForTheFirstBound() {
        return mRetrieveOffersForTheFirstBound;
    }

    public void setPersonalizedRanking(boolean personalizedRanking) {
        mPersonalizedRanking = personalizedRanking;
    }

    public boolean getPersonalizedRanking() {
        return mPersonalizedRanking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageSearchRequestData that = (ImageSearchRequestData) o;
        return mImageUri.equals(that.mImageUri);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mImageUri);
        result = 31 * result + Objects.hash(mCoordinates);
        return result;
    }

}