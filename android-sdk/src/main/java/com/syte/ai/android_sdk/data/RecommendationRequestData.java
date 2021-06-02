package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.RecommendationFeature;
import com.syte.ai.android_sdk.enums.RecommendationProduct;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.Objects;

public class RecommendationRequestData {

    public String sku;
    public String imageUrl;
    public RecommendationReturnField fieldsToReturn;
    public RecommendationFeature feature;
    public RecommendationProduct product;

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
