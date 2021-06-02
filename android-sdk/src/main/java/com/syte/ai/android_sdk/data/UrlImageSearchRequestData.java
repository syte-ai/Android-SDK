package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.SyteProductType;

import java.util.Objects;

public class UrlImageSearchRequestData {

    public String imageUrl;
    public SyteProductType productType;
    public String sku;

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
