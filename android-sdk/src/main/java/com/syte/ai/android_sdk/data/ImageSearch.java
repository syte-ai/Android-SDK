package com.syte.ai.android_sdk.data;

import android.net.Uri;

import com.syte.ai.android_sdk.data.result.offers.BoundsResult;

/**
 * Class that is used to configure the 'wild' image search.
 */
public class ImageSearch {

    private final Uri mImageUri;
    private CropCoordinates mCoordinates;
    private boolean mRetrieveOffersForTheFirstBound = true;
    private boolean mPersonalizedRanking = false;

    /**
     * @param imageUri - a path to the image on device.
     */
    public ImageSearch(Uri imageUri) {
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
     * {@link #setRetrieveItemsForTheFirstBound(boolean)} method was called with the false value passed.
     * @param coordinates - coordinates to use for crop. See {@link CropCoordinates}
     */
    public void setFirstBoundItemsCoordinates(CropCoordinates coordinates) {
        this.mCoordinates = coordinates;
    }

    /**
     * @return {@link CropCoordinates}
     */
    public CropCoordinates getFirstBoundItemsCoordinates() {
        return mCoordinates;
    }

    /**
     * Indicates whether to retrive the offers list for the first retrieved bound automatically.
     * The list can be retrieved by calling {@link BoundsResult#getItemsForFirstBound()}
     * The functionality is enabled by default.
     * @param retrieveItemsForTheFirstBound indicates whether to retrieve items for the first bound automatically
     */
    public void setRetrieveItemsForTheFirstBound(boolean retrieveItemsForTheFirstBound) {
        mRetrieveOffersForTheFirstBound = retrieveItemsForTheFirstBound;
    }

    /**
     * @return true if the automatic offers retrieval for the first bound is enabled. False otherwise.
     */
    public boolean isRetrieveItemsForTheFirstBound() {
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