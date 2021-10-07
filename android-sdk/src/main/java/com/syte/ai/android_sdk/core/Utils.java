package com.syte.ai.android_sdk.core;

import android.text.TextUtils;
import android.util.Pair;

import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Utils {

    static byte[] getFileBytes(File file) throws IOException {
        int size = (int) file.length();
        byte[] bytes = new byte[size];

        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        buf.read(bytes, 0, bytes.length);
        buf.close();

        return bytes;
    }

    public static String viewedProductsString(Set<String> viewedProducts) {
        if (viewedProducts.isEmpty()) {
            return null;
        }
        return TextUtils.join(",", viewedProducts);
    }

    static String textSearchTermsString(List<String> terms) {
        if (terms.isEmpty()) {
            return null;
        }

        return TextUtils.join(",", terms);
    }

    static String viewedProductsJSONArray(Set<String> viewedProducts) {
        if (viewedProducts.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String sku : viewedProducts) {
            stringBuilder.append("\"");
            stringBuilder.append(sku);
            stringBuilder.append("\"");
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    static String viewedProductsJSONArray(String sku) {
        if (sku == null) {
            return null;
        }

        return String.format("[%s]", sku);
    }

    static String generateFiltersString(Map<String, List<String>> filters) {
        if (filters.isEmpty()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Map.Entry<String, List<String>> filter : filters.entrySet()) {
            stringBuilder.append(String.format("\"%s\":[", filter.getKey()));
            for (String filterValue : filter.getValue()) {
                stringBuilder.append(String.format("\"%s\",", filterValue));
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append("]");
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
