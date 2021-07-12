package com.syte.ai.android_sdk.data;

import androidx.annotation.Nullable;

import com.syte.ai.android_sdk.enums.SyteProductType;

import java.util.Objects;

public class UrlImageSearchRequestData {

    private final String mImageUrl;
    private final SyteProductType mProductType;
    @Nullable private String mSku;
    private boolean mRetrieveOffersForTheFirstBound = true;
    private CropCoordinates mCoordinates;
    private boolean mPersonalizedRanking = false;

    public UrlImageSearchRequestData(String imageUrl, SyteProductType productType) {
        this.mImageUrl = imageUrl;
        this.mProductType = productType;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public SyteProductType getProductType() {
        return mProductType;
    }

    public void setSku(String sku) {
        this.mSku = sku;
    }

    @Nullable
    public String getSku() {
        return mSku;
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
        UrlImageSearchRequestData that = (UrlImageSearchRequestData) o;
        return mImageUrl.equals(that.mImageUrl) &&
                mProductType == that.mProductType &&
                Objects.equals(mSku, that.mSku) &&
                mRetrieveOffersForTheFirstBound == that.mRetrieveOffersForTheFirstBound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mImageUrl, mProductType, mSku);
    }

}
