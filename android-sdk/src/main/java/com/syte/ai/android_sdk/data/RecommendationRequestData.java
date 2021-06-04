package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationFeature;
import com.syte.ai.android_sdk.enums.RecommendationProduct;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.Objects;

public class RecommendationRequestData {

    public String sku;
    public String imageUrl;
    private RecommendationReturnField fieldsToReturn;
    private RecommendationFeature feature;
    private RecommendationProduct product;

    public RecommendationRequestData(String sku, String imageUrl) {
        this.sku = sku;
        this.imageUrl = imageUrl;
    }

    public String getSku() {
        return sku;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setFeature(RecommendationFeature feature) {
        this.feature = feature;
    }

    public RecommendationFeature getFeature() {
        return feature;
    }

    public void setProduct(RecommendationProduct product) {
        this.product = product;
    }

    public RecommendationProduct getProduct() {
        return product;
    }

    public void setFieldsToReturn(RecommendationReturnField fieldsToReturn) {
        this.fieldsToReturn = fieldsToReturn;
    }

    public RecommendationReturnField getFieldsToReturn() {
        return fieldsToReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationRequestData that = (RecommendationRequestData) o;
        return sku.equals(that.sku) &&
                imageUrl.equals(that.imageUrl) &&
                fieldsToReturn == that.fieldsToReturn &&
                feature == that.feature &&
                product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sku, imageUrl, fieldsToReturn, feature, product);
    }

}
