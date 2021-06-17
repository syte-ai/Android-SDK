package com.syte.ai.android_sdk.internal;

import android.os.CountDownTimer;

import com.google.gson.GsonBuilder;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.util.SyteLogger;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(SYTE_URL)
                .client(client)
                .build();
    }

    public void applyConfiguration(SyteConfiguration configuration) {
        SyteLogger.v(TAG, "applyConfiguration");
        this.mConfiguration = configuration;
    }

    protected abstract void startCountDown();

    protected abstract void resetTimer();

    abstract SyteResult<AccountDataService> initialize() throws IOException;

    abstract void initializeAsync(SyteCallback<AccountDataService> callback);

    abstract SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData requestData, AccountDataService accountDataService) throws IOException;

    abstract void getBoundsAsync(UrlImageSearchRequestData requestData, AccountDataService accountDataService, SyteCallback<BoundsResult> callback);

    abstract SyteResult<OffersResult> getOffers(Bound bound);

    abstract void getOffersAsync(Bound bound, SyteCallback<OffersResult> callback);

    abstract void wildImageSearch(ImageSearchRequestData requestData);

    abstract void wildImageSearchAsync(ImageSearchRequestData requestData, SyteCallback callback);

}
