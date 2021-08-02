package com.syte.ai.android_sdk.data;

import androidx.annotation.Nullable;

import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.enums.SyteProductType;

import java.util.Objects;

/**
 * Class that is used to configure the Url image search.
 */
public class UrlImageSearchRequestData {

    private final String mImageUrl;
    private final SyteProductType mProductType;
    @Nullable private String mSku = null;
    private boolean mRetrieveOffersForTheFirstBound = true;
    private CropCoordinates mCoordinates;
    private boolean mPersonalizedRanking = false;

    /**
     * @param imageUrl - image URL
     * @param productType - {@link SyteProductType}
     */
    public UrlImageSearchRequestData(String imageUrl, SyteProductType productType) {
        this.mImageUrl = imageUrl;
        this.mProductType = productType;
    }

    /**
     * @return image URL
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * @return {@link SyteProductType}
     */
    public SyteProductType getProductType() {
        return mProductType;
    }

    /**
     * @param sku - product ID
     */
    public void setSku(String sku) {
        this.mSku = sku;
    }

    /**
     * @return - product ID
     */
    @Nullable
    public String getSku() {
        return mSku;
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
