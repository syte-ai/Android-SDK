package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationFeature;
import com.syte.ai.android_sdk.enums.RecommendationProduct;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.Objects;

public class RecommendationRequestData {

    private String mSku;
    private String mImageUrl;
    private RecommendationReturnField mFieldsToReturn;
    private RecommendationFeature mFeature;
    private RecommendationProduct mProduct;

    public RecommendationRequestData(String sku, String imageUrl) {
        this.mSku = sku;
        this.mImageUrl = imageUrl;
    }

    public String getSku() {
        return mSku;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setFeature(RecommendationFeature feature) {
        this.mFeature = feature;
    }

    public RecommendationFeature getFeature() {
        return mFeature;
    }

    public void setProduct(RecommendationProduct product) {
        this.mProduct = product;
    }

    public RecommendationProduct getProduct() {
        return mProduct;
    }

    public void setFieldsToReturn(RecommendationReturnField fieldsToReturn) {
        this.mFieldsToReturn = fieldsToReturn;
    }

    public RecommendationReturnField getFieldsToReturn() {
        return mFieldsToReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationRequestData that = (RecommendationRequestData) o;
        return mSku.equals(that.mSku) &&
                mImageUrl.equals(that.mImageUrl) &&
                mFieldsToReturn == that.mFieldsToReturn &&
                mFeature == that.mFeature &&
                mProduct == that.mProduct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mSku, mImageUrl, mFieldsToReturn, mFeature, mProduct);
    }

}
