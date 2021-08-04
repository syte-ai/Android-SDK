package com.syte.ai.android_sdk.app;

import java.util.Set;

public class Utils {

    public static String viewedProductsString(Set<String> viewedProducts) {
        StringBuilder sb = new StringBuilder();
        for (String sku : viewedProducts) {
            sb.append(sku);
            sb.append(",");
        }
        return sb.toString();
    }

}
