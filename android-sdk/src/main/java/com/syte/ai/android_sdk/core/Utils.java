package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
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

    static ImageProcessor.Scale getImageScale(SytePlatformSettings sytePlatformSettings) {
        String imageScale = sytePlatformSettings
                .getData()
                .getProducts()
                .getSyteapp()
                .getFeatures()
                .getCameraHandler()
                .getPhotoReductionSize();

        ImageProcessor.Scale scale;

        switch (imageScale.toLowerCase()) {
            case "small":
                scale = ImageProcessor.Scale.SMALL;
                break;
            case "medium":
                scale = ImageProcessor.Scale.MEDIUM;
                break;
            case "large":
                scale = ImageProcessor.Scale.LARGE;
                break;
            default:
                scale = ImageProcessor.Scale.MEDIUM;
                break;
        }

        return scale;
    }

    public static String viewedProductsString(Set<String> viewedProducts) {
        if (viewedProducts.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String sku : viewedProducts) {
            sb.append(sku);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    static String textSearchTermsString(List<String> terms) {
        if (terms.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String term : terms) {
            sb.append(term);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
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

    static String generateFiltersString(List<String> filters) {
        if (filters.isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        if (!filters.isEmpty()) {
            for (String filter : filters) {
                builder.append(filter);
                builder.append(",");
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }

}
