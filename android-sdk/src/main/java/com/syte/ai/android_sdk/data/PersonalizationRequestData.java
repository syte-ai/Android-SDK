package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationReturnField;

public class PersonalizationRequestData {

    private String mSyteUrlReferer = "mobile_sdk";
    private int mLimit = 7;
    private String mModelVersion = "A";
    private RecommendationReturnField mFieldsToReturn = RecommendationReturnField.ALL;

    public void setFieldsToReturn(RecommendationReturnField fieldsToReturn) {
        this.mFieldsToReturn = fieldsToReturn;
    }

    public RecommendationReturnField getFieldsToReturn() {
        return mFieldsToReturn;
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

    public void setModelVersion(String modelVersion) {
        mModelVersion = modelVersion;
    }

    public String getModelVersion() {
        return mModelVersion;
    }

}
