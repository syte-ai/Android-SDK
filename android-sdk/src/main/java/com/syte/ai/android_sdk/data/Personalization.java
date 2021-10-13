package com.syte.ai.android_sdk.data;

import androidx.annotation.Nullable;

import com.syte.ai.android_sdk.core.Syte;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that is used to configure the Personalization requests.
 * NOTE: you MUST add at least one viewed product in {@link Syte#addViewedProduct(String)}
 * for the Personalization functionality to work.
 */
public class Personalization {

    private String mSyteUrlReferer = "mobile_sdk";
    private int mLimit = 7;
    private String mModelVersion = "A";
    @Nullable private String mSku = null;
    private RecommendationReturnField mFieldsToReturn = RecommendationReturnField.ALL;
    private Map<String, String> mOptions = new HashMap<>();

    /**
     * Configure what fields must be returned in response. All fields will be returned by default.
     * In case the value is changed, the result will only contain the chosen fields. All other ones will be null!
     * @param fieldsToReturn - {@link RecommendationReturnField}
     */
    public void setFieldsToReturn(RecommendationReturnField fieldsToReturn) {
        this.mFieldsToReturn = fieldsToReturn;
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

    /**
     * Set current product id to get results for.
     * This method should only be called in the situation when the
     * local storage usage is disabled. ({@link com.syte.ai.android_sdk.core.SyteConfiguration#enableLocalStorage(boolean)})
     * Otherwise, please, use the {@link Syte#addViewedProduct(String)} method to add product IDs
     * (They will be used for the personalization calls.)
     * @param sku - product id
     */
    public void setSku(String sku) {
        mSku = sku;
    }

    /**
     * Get current product ID.
     * @return current product ID.
     */
    public String getSku() {
        return mSku;
    }

}
