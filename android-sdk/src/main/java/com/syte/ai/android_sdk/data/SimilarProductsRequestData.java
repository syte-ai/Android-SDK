package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.Objects;

public class SimilarProductsRequestData {

    private String mSku;
    private String mImageUrl;
    private boolean mPersonalizedRanking = false;
    private String mSyteUrlReferer = "mobile_sdk";
    private int mLimit = 7;
    private RecommendationReturnField mFieldsToReturn = RecommendationReturnField.ALL;

    public SimilarProductsRequestData(String sku, String imageUrl) {
        this.mSku = sku;
        this.mImageUrl = imageUrl;
    }

    public String getSku() {
        return mSku;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setFieldsToReturn(RecommendationReturnField fieldsToReturn) {
        this.mFieldsToReturn = fieldsToReturn;
    }

    public RecommendationReturnField getFieldsToReturn() {
        return mFieldsToReturn;
    }

    public void setPersonalizedRanking(boolean personalizedRanking) {
        mPersonalizedRanking = personalizedRanking;
    }

    public boolean getPersonalizedRanking() {
        return mPersonalizedRanking;
    }

    public void setSyteUrlReferer(String syteUrlReferer) {
        mSyteUrlReferer = syteUrlReferer;
    }

    public String getSyteUrlReferer() {
        return mSyteUrlReferer;
    }

    public void setLimit(int limit) {
        mLimit = limit;
    }

    public int getLimit() {
        return mLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimilarProductsRequestData that = (SimilarProductsRequestData) o;
        return mSku.equals(that.mSku) &&
                mImageUrl.equals(that.mImageUrl) &&
                mFieldsToReturn == that.mFieldsToReturn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mSku, mImageUrl, mFieldsToReturn);
    }

}
