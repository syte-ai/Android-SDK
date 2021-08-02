package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationReturnField;

/**
 * Class that is used to configure the Personalization requests.
 * NOTE: you MUST add at least one viewed product in {@link com.syte.ai.android_sdk.core.InitSyte#addViewedProduct(String)}
 * for the Personalization functionality to work.
 */
public class PersonalizationRequestData {

    private String mSyteUrlReferer = "mobile_sdk";
    private int mLimit = 7;
    private String mModelVersion = "A";
    private RecommendationReturnField mFieldsToReturn = RecommendationReturnField.ALL;

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

    /**
     * Default value - A
     * @param modelVersion
     */
    public void setModelVersion(String modelVersion) {
        mModelVersion = modelVersion;
    }

    /**
     * @return model version.
     */
    public String getModelVersion() {
        return mModelVersion;
    }

}
