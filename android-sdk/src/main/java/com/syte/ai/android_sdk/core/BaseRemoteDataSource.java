package com.syte.ai.android_sdk.core;

import android.content.Context;
import android.os.CountDownTimer;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.util.SyteLogger;

import java.io.IOException;

import retrofit2.Retrofit;

abstract class BaseRemoteDataSource {

    protected static final String TAG = "SyteRemoteDataSource";

    protected static final int SESSION_TIMEOUT_SEC = 30 * 60;
    protected static final String SYTE_URL = "https://cdn.syteapi.com";
    protected static final String EXIF_REMOVAL_URL = "https://imagemod.syteapi.com";

    protected CountDownTimer mTimer;
    protected SyteConfiguration mConfiguration;
    protected Retrofit mRetrofit;
    protected Retrofit mExifRemovalRetrofit;

    BaseRemoteDataSource(SyteConfiguration configuration) {
        //TODO initialize timer
        mConfiguration = configuration;
        mRetrofit = RetrofitBuilder.build(SYTE_URL);
        mExifRemovalRetrofit = RetrofitBuilder.build(EXIF_REMOVAL_URL);
    }

    public void applyConfiguration(SyteConfiguration configuration) {
        SyteLogger.v(TAG, "applyConfiguration");
        this.mConfiguration = configuration;
        //TODO set configuration in all data sources
    }

    protected void startCountDown() {}
    protected void resetTimer() {}

    abstract SyteResult<AccountDataService> initialize() throws IOException;

    abstract void initializeAsync(SyteCallback<AccountDataService> callback);

    abstract SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData requestData, AccountDataService accountDataService) throws IOException;

    abstract void getBoundsAsync(UrlImageSearchRequestData requestData, AccountDataService accountDataService, SyteCallback<BoundsResult> callback);

    abstract SyteResult<OffersResult> getOffers(Bound bound,
                                                CropCoordinates cropCoordinates,
                                                AccountDataService accountDataService);

    abstract void getOffersAsync(Bound bound,
                                 CropCoordinates cropCoordinates,
                                 AccountDataService accountDataService,
                                 SyteCallback<OffersResult> callback);

    abstract SyteResult<BoundsResult> getBoundsWild(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService
    ) throws IOException;

    abstract void getBoundsWildAsync(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService,
            SyteCallback<BoundsResult> callback
    ) throws IOException;
}
