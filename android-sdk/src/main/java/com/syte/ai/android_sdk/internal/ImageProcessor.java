package com.syte.ai.android_sdk.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;

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

    enum Scale {
        SMALL, MEDIUM, LARGE
    }

    static final String COMPRESSED_IMAGE_DIR = "/compress";
    static final int SCALE_QUALITY = 20;

    File compress(Scale scale, Context context, Bitmap bitmap) {
        //TODO handle errors here
        Compress compress = Compress.Companion.with(context, bitmap);
        compress.setFormat(Bitmap.CompressFormat.JPEG);
        compress.setQuality(SCALE_QUALITY);
        String dir = context.getFilesDir().getAbsolutePath() + COMPRESSED_IMAGE_DIR;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
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

        Log.i(TAG, "Compressed max height: " + height);
        Log.i(TAG, "Compressed max width: " + width);
        compressor.setMaxHeight(height);
        compressor.setMaxWidth(width);
        return compressor.get();
    }

    @Nullable
    Bitmap rotateImageIfNeeded(Context context, Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            // TODO handle error here
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

    private Matrix handleRotation(InputStream inputStream) {
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(inputStream);
        } catch (IOException e) {
            //TODO handle error here
        }
        int orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        int rotation = exifToDegrees(orientation);

        Log.i(TAG, "Orientation: " + orientation);
        Log.i(TAG, "Rotation: " + rotation);

        Matrix matrix = new Matrix();
        if (rotation != 0) {
            matrix.postRotate(rotation);
        }

        return matrix;
    }

    @Nullable
    private Bitmap getSourceBitmap(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                return ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.getContentResolver(), uri));
            } catch (IOException e) {
                //TODO handle error here
            }
        } else {
            try {
                return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            } catch (IOException e) {
                //TODO handle error here
            }
        }

        return null;
    }

}
