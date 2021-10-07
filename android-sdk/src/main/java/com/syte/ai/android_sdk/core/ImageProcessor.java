package com.syte.ai.android_sdk.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;

import com.syte.ai.android_sdk.enums.ImageScale;
import com.syte.ai.android_sdk.exceptions.SyteGeneralException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;
import com.syte.ai.android_sdk.util.SyteLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import me.shouheng.compress.Compress;
import me.shouheng.compress.strategy.Strategies;
import me.shouheng.compress.strategy.compress.Compressor;
import me.shouheng.compress.strategy.config.ScaleMode;

class ImageProcessor {

    private static final String TAG = ImageProcessor.class.getSimpleName();

    static final String COMPRESSED_IMAGE_DIR = "/compress";
    static final int SCALE_QUALITY = 20;
    static final int SMALL_IMAGE_MAX_SIZE = 300_000;
    static final float SMALL_IMAGE_MAX_WIDTH = 500f;
    static final float SMALL_IMAGE_MAX_HEIGHT = 1000f;
    static final float MEDIUM_IMAGE_MAX_WIDTH = 1400f;
    static final float MEDIUM_IMAGE_MAX_HEIGHT = 1400f;
    static final float LARGE_IMAGE_MAX_WIDTH = 2000f;
    static final float LARGE_IMAGE_MAX_HEIGHT = 2000f;

    File compress(Context context, long size, Bitmap bitmap, ImageScale scale) throws SyteGeneralException {
        Compress compress = Compress.Companion.with(context, bitmap);
        compress.setFormat(Bitmap.CompressFormat.JPEG);
        compress.setQuality(SCALE_QUALITY);
        String dir = context.getFilesDir().getAbsolutePath() + COMPRESSED_IMAGE_DIR;
        File file = new File(dir);
        if (!file.exists()) {
            boolean result = file.mkdir();
            if (!result) {
                throw new SyteGeneralException("Could not create file. Please, check your permissions.");
            }
        }
        compress.setTargetDir(dir);
        Compressor compressor = compress.strategy(Strategies.INSTANCE.compressor());

        if (bitmap.getWidth() > bitmap.getHeight()) {
            compressor.setScaleMode(ScaleMode.SCALE_LARGER);
        } else {
            compressor.setScaleMode(ScaleMode.SCALE_SMALLER);
        }

        float height = 0;
        float width = 0;
        ImageScale resultScale;

        if (size > SMALL_IMAGE_MAX_SIZE) {
            resultScale = scale;
        } else {
            resultScale = ImageScale.SMALL;
        }

        switch (resultScale) {
            case SMALL:
                height = SMALL_IMAGE_MAX_HEIGHT;
                width = SMALL_IMAGE_MAX_WIDTH;
                break;
            case MEDIUM:
                height = MEDIUM_IMAGE_MAX_HEIGHT;
                width = MEDIUM_IMAGE_MAX_WIDTH;
                break;
            case LARGE:
                height = LARGE_IMAGE_MAX_HEIGHT;
                width = LARGE_IMAGE_MAX_WIDTH;
                break;
        }

        SyteLogger.i(TAG, "Compressed max height: " + height);
        SyteLogger.i(TAG, "Compressed max width: " + width);
        compressor.setMaxHeight(height);
        compressor.setMaxWidth(width);
        return compressor.get();
    }

    @Nullable
    Bitmap rotateImageIfNeeded(Context context, Uri uri) throws SyteGeneralException {
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            throw new SyteGeneralException("Could not open stream for the URI: " + e.getMessage());
        }

        Matrix matrix = handleRotation(inputStream);
        Bitmap srcBitmap = getSourceBitmap(context, uri);

        if (srcBitmap != null) {
            return Bitmap.createBitmap(
                    srcBitmap,
                    0,
                    0,
                    srcBitmap.getWidth(),
                    srcBitmap.getHeight(),
                    matrix,
                    true);
        }

        return null;
    }

    private int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Matrix handleRotation(InputStream inputStream) throws SyteGeneralException {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(inputStream);
        } catch (IOException e) {
            throw new SyteGeneralException("Could not handle image rotation: " + e.getMessage());
        }
        int orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        int rotation = exifToDegrees(orientation);

        SyteLogger.i(TAG, "Orientation: " + orientation);
        SyteLogger.i(TAG, "Rotation: " + rotation);

        Matrix matrix = new Matrix();
        if (rotation != 0) {
            matrix.postRotate(rotation);
        }

        return matrix;
    }

    @Nullable
    private Bitmap getSourceBitmap(Context context, Uri uri) throws SyteGeneralException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                return ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.getContentResolver(), uri));
            } catch (IOException e) {
                throw new SyteGeneralException("Could not decode bitmap: " + e.getMessage());
            }
        } else {
            try {
                return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            } catch (IOException e) {
                throw new SyteGeneralException("Could not decode bitmap: " + e.getMessage());
            }
        }
    }

}
