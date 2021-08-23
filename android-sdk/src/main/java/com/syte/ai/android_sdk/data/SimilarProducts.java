package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.Objects;

public class SimilarProducts {

    private String mSku;
    private String mImageUrl;
    private boolean mPersonalizedRanking = false;
    private String mSyteUrlReferer = "mobile_sdk";
    private int mLimit = 7;
    private RecommendationReturnField mFieldsToReturn = RecommendationReturnField.ALL;

    /**
     * @param sku - product ID
     * @param imageUrl - image URL
     */
    public SimilarProducts(String sku, String imageUrl) {
        this.mSku = sku;
        this.mImageUrl = imageUrl;
    }

    /**
     * @return product ID
     */
    public String getSku() {
        return mSku;
    }

    /**
     * @return image URL
     */
    public String getImageUrl() {
        return mImageUrl;
    }

    /**
     * Configure what fields must be returned in response. All fields will be returned by default.
     * In case the value is changed, the result will only contain the chosen fields. All other ones will be null!
     * @param fieldsToReturn - {@link RecommendationReturnField}
     */
    public void setFieldsToReturn(RecommendationReturnField fieldsToReturn) {
        this.mFieldsToReturn = fieldsToReturn;
    }

    /**
     * @return {@link RecommendationReturnField}
     */
    public RecommendationReturnField getFieldsToReturn() {
        return mFieldsToReturn;
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
     * Set Syte Url referer
     * @param syteUrlReferer
     */
    public void setSyteUrlReferer(String syteUrlReferer) {
        mSyteUrlReferer = syteUrlReferer;
    }

    /**
     * @return Syte url referer.
     */
    public String getSyteUrlReferer() {
        return mSyteUrlReferer;
    }

    /**
     * Set max items to return.
     * @param limit
     */
    public void setLimit(int limit) {
        mLimit = limit;
    }

    /**
     * @return max items to return.
     */
    public int getLimit() {
        return mLimit;
    }

}
