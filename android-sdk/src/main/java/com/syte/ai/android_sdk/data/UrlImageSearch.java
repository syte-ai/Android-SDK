package com.syte.ai.android_sdk.data;

import androidx.annotation.Nullable;

import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.SyteProductType;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that is used to configure the Url image search.
 */
public class UrlImageSearch {

    private final String mImageUrl;
    private final SyteProductType mProductType;
    @Nullable private String mSku = null;
    private boolean mRetrieveOffersForTheFirstBound = true;
    private CropCoordinates mCoordinates;
    private boolean mPersonalizedRanking = false;
    private Map<String, String> mOptions = new HashMap<>();
    private Catalog mCatalog;

    /**
     * @param imageUrl - image URL
     * @param productType - {@link SyteProductType}
     */
    public UrlImageSearch(String imageUrl, SyteProductType productType) {
        this.mImageUrl = imageUrl;
        this.mProductType = productType;
    }

    /**
     * Add additional query parameters to request.
     * @param key query name
     * @param value query value
     */
    public void addOption(String key, String value) {
        mOptions.put(key, value);
    }

    /**
     * Get additional query parameters.
     * @return additional query parameters
     */
    public Map<String, String> getOptions() {
        return mOptions;
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
     * @param retrieveItemsForTheFirstBound
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

    /**
     * Set catalog that will be used in requests.
     * No catalog will be added to the requests by default
     * @param catalog {@link Catalog}
     */
    public void setCatalog(Catalog catalog) {
        mCatalog = catalog;
    }

    /**
     * Get catalog
     * @return {@link Catalog}
     */
    public Catalog getCatalog() {
        return mCatalog;
    }

}
