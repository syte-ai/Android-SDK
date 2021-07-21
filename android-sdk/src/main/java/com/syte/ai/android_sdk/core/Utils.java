package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.data.result.account.AccountDataService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class Utils {

    static byte[] getFileBytes(File file) throws IOException {
        int size = (int) file.length();
        byte[] bytes = new byte[size];

        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
        buf.read(bytes, 0, bytes.length);
        buf.close();

        return bytes;
    }

    static ImageProcessor.Scale getImageScale(AccountDataService accountDataService) {
        String imageScale = accountDataService
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

}
