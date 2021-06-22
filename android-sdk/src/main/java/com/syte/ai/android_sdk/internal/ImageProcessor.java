package com.syte.ai.android_sdk.internal;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;

import me.shouheng.compress.Compress;
import me.shouheng.compress.strategy.Strategies;
import me.shouheng.compress.strategy.compress.Compressor;
import me.shouheng.compress.strategy.config.ScaleMode;

class ImageProcessor {

    enum Scale {
        SMALL, MEDIUM, LARGE
    }

    static final String COMPRESSED_IMAGE_DIR = "/compress";
    static final int SCALE_QUALITY = 20;

    public File compress(Scale scale, Context context, File imageFile) {
        //TODO handle errors here
        Compress compress = Compress.Companion.with(context, imageFile);
        compress.setFormat(Bitmap.CompressFormat.JPEG);
        compress.setQuality(SCALE_QUALITY);
        String dir = context.getFilesDir().getAbsolutePath() + COMPRESSED_IMAGE_DIR;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        compress.setTargetDir(dir);
        Compressor compressor = compress.strategy(Strategies.INSTANCE.compressor());
        compressor.setScaleMode(ScaleMode.SCALE_SMALLER);

        float height = 0;
        float width = 0;
        Scale resultScale;

        if (file.length() > 300_000) {
            resultScale = scale;
        } else {
            resultScale = Scale.SMALL;
        }

        switch (resultScale) {
            case SMALL:
                height = 1000f;
                width = 500f;
                break;
            case MEDIUM:
                height = 1400f;
                width = 1400f;
                break;
            case LARGE:
                height = 2000f;
                width = 2000f;
                break;
        }

        compressor.setMaxHeight(height);
        compressor.setMaxWidth(width);
        return compressor.get();
    }

}
