package com.syte.ai.android_sdk.data;

import android.net.Uri;

import com.syte.ai.android_sdk.data.result.offers.BoundsResult;

import java.util.Objects;

/**
 * Class that is used to configure the 'wild' image search.
 */
public class ImageSearchRequestData {

    private final Uri mImageUri;
    private CropCoordinates mCoordinates;
    private boolean mRetrieveOffersForTheFirstBound = true;
    private boolean mPersonalizedRanking = false;

    /**
     * @param imageUri - a path to the image on device.
     */
    public ImageSearchRequestData(Uri imageUri) {
        this.mImageUri = imageUri;
    }

    /**
     * @return image path.
     */
    public Uri getImageUri() {
        return mImageUri;
    }

    /**
     * Set coordinates for the offers for the first bound. Will be disregarded if
     * {@link #setRetrieveOffersForTheFirstBound(boolean)} method was called with the false value passed.
     * @param coordinates - coordinates to use for crop. See {@link CropCoordinates}
     */
    public void setFirstBoundOffersCoordinates(CropCoordinates coordinates) {
        this.mCoordinates = coordinates;
    }

    /**
     * @return {@link CropCoordinates}
     */
    public CropCoordinates getFirstBoundOffersCoordinates() {
        return mCoordinates;
    }

    /**
     * Indicates whether to retrive the offers list for the first retrieved bound automatically.
     * The list can be retrieved by calling {@link BoundsResult#getFirstBoundOffersResult()}
     * The functionality is enabled by default.
     * @param retrieveOffersForTheFirstBound
     */
    public void setRetrieveOffersForTheFirstBound(boolean retrieveOffersForTheFirstBound) {
        mRetrieveOffersForTheFirstBound = retrieveOffersForTheFirstBound;
    }

    /**
     * @return true if the automatic offers retrieval for the first bound is enabled. False otherwise.
     */
    public boolean isRetrieveOffersForTheFirstBound() {
        return mRetrieveOffersForTheFirstBound;
    }

    /**
     * @param personalizedRanking true to include the list of viewed during the session products. False otherwise.
     */
    public void setPersonalizedRanking(boolean personalizedRanking) {
        mPersonalizedRanking = personalizedRanking;
    }

    /**
     * @return true if the list of viewed products is configured to be included into the requests. False otherwise.
     */
    public boolean getPersonalizedRanking() {
        return mPersonalizedRanking;
    }

}