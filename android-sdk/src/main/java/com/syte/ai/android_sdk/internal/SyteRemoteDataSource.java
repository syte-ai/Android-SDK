package com.syte.ai.android_sdk.internal;

import android.os.CountDownTimer;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;

import retrofit2.Retrofit;

class SyteRemoteDataSource extends BaseRemoteDataSource{


    SyteRemoteDataSource(SyteConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected void startCountDown() {

    }

    @Override
    protected void resetTimer() {

    }

    @Override
    SyteResult initialize() {
        return null;
    }

    @Override
    void initializeAsync(SyteCallback callback) {

    }

    @Override
    SyteResult urlImageSearch(UrlImageSearchRequestData requestData) {
        return null;
    }

    @Override
    void urlImageSearchAsync(UrlImageSearchRequestData requestData, SyteCallback callback) {

    }

    @Override
    void wildImageSearch(ImageSearchRequestData requestData) {

    }

    @Override
    void wildImageSearchAsync(ImageSearchRequestData requestData, SyteCallback callback) {

    }

}
