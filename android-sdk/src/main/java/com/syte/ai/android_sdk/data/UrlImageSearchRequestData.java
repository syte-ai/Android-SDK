package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.SyteProductType;

import java.util.Objects;

public class UrlImageSearchRequestData {

    private String imageUrl;
    private SyteProductType productType;
    private String sku;

    public UrlImageSearchRequestData(String imageUrl, SyteProductType productType) {
        this.imageUrl = imageUrl;
        this.productType = productType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public SyteProductType getProductType() {
        return productType;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlImageSearchRequestData that = (UrlImageSearchRequestData) o;
        return imageUrl.equals(that.imageUrl) &&
                productType == that.productType &&
                Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageUrl, productType, sku);
    }

}
