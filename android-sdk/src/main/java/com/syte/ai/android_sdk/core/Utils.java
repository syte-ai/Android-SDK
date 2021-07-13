package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.data.result.account.AccountDataService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class Utils {

    static byte[] getFileBytes(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];

        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            //TODO handle error here
        }

        return bytes;
    }

    static ImageProcessor.Scale getImageScale(AccountDataService accountDataService) {
        //TODO check for nulls here
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
