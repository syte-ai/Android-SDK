package com.syte.ai.android_sdk.internal;

import android.os.CountDownTimer;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.util.SyteLogger;

import retrofit2.Retrofit;

abstract class BaseRemoteDataSource {

    protected static final String TAG = "SyteRemoteDataSource";

    protected static final int SESSION_TIMEOUT_SEC = 30 * 60;
    protected static final String SYTE_URL = "https://cdn.syteapi.com";

    protected CountDownTimer mTimer;
    protected SyteConfiguration mConfiguration;
    protected Retrofit mRetrofit;

    BaseRemoteDataSource(SyteConfiguration configuration) {
        //TODO initialize timer
        mConfiguration = configuration;

        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(SYTE_URL)
                .build();
    }

    public void applyConfiguration(SyteConfiguration configuration) {
        SyteLogger.v(TAG, "applyConfiguration");
        this.mConfiguration = configuration;
    }

    protected abstract void startCountDown();

    protected abstract void resetTimer();

    abstract SyteResult initialize();

    abstract void initializeAsync(SyteCallback callback);

    abstract SyteResult urlImageSearch(UrlImageSearchRequestData requestData);

    abstract void urlImageSearchAsync(UrlImageSearchRequestData requestData, SyteCallback callback);

    abstract void wildImageSearch(ImageSearchRequestData requestData);

    abstract void wildImageSearchAsync(ImageSearchRequestData requestData, SyteCallback callback);

}
