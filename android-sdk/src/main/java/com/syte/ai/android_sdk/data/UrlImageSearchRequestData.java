package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.SyteProductType;

import java.util.Objects;

public class UrlImageSearchRequestData {

    private String mImageUrl;
    private SyteProductType mProductType;
    private String mSku;

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

    public String getSku() {
        return mSku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlImageSearchRequestData that = (UrlImageSearchRequestData) o;
        return mImageUrl.equals(that.mImageUrl) &&
                mProductType == that.mProductType &&
                Objects.equals(mSku, that.mSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mImageUrl, mProductType, mSku);
    }

}
