package com.syte.ai.android_sdk.internal;

import android.os.CountDownTimer;

import com.syte.ai.android_sdk.data.SyteConfiguration;

import retrofit2.Retrofit;

class SyteRemoteDataSource {

    private static final int SESSION_TIMEOUT_SEC = 30 * 60;

    private CountDownTimer mTimer;
    private SyteConfiguration mConfiguration;
    private Retrofit mRetrofit;

    SyteRemoteDataSource(SyteConfiguration configuration) {
        //TODO initialize timer

        mConfiguration = configuration;
    }

    public void applyConfiguration(SyteConfiguration configuration) {
        this.mConfiguration = configuration;
    }

    private void startCountDown() {}

    private void resetTimer() {}

}
