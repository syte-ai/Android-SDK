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

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

abstract class BaseRemoteDataSource {

    protected static final String TAG = "SyteRemoteDataSource";

    protected SyteConfiguration mConfiguration;

    public void setConfiguration(SyteConfiguration configuration) {
        SyteLogger.v(TAG, "applyConfiguration");
        this.mConfiguration = configuration;
    }

    protected void renewTimestamp() {
        mConfiguration.getStorage().renewSessionIdTimestamp();
    }

    protected  <T> SyteResult<T> handleEmptyBody(Response<ResponseBody> result) throws IOException {
        SyteResult<T> syteResult = new SyteResult<>();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        } else {
            syteResult.errorMessage = "Result body is null";
        }
        syteResult.resultCode = result.code();
        return syteResult;
    }

    protected <T> SyteResult<T> handleOnFailure(Throwable t) {
        SyteResult<T> syteResult = new SyteResult<>();
        syteResult.errorMessage = t.getMessage();
        return syteResult;
    }

    protected <T> SyteResult<T> handleException(
            Response<ResponseBody> result,
            Throwable e
    ) {
        SyteResult<T> syteResult = new SyteResult<>();
        if (result != null && !result.isSuccessful()) {
            syteResult.errorMessage = result.message();
            syteResult.resultCode = result.code();
        }
        syteResult.errorMessage = e.getMessage();

        return syteResult;
    }

}
