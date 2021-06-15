package com.syte.ai.android_sdk.internal;

import android.os.CountDownTimer;
import android.util.Log;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

class SyteRemoteDataSource extends BaseRemoteDataSource{

    private final SyteService mSyteService;

    SyteRemoteDataSource(SyteConfiguration configuration) {
        super(configuration);
        mSyteService = mRetrofit.create(SyteService.class);
    }

    @Override
    protected void startCountDown() {

    }

    @Override
    protected void resetTimer() {

    }

    @Override
    SyteResult<AccountDataService> initialize() throws IOException {
        Response<AccountDataService> accountDataService = mSyteService.initialize("9186").execute();
        SyteResult<AccountDataService> syteResult = new SyteResult<>();
        syteResult.data = accountDataService.body();
        syteResult.resultCode = accountDataService.code();
        syteResult.isSuccessful = accountDataService.isSuccessful();
        return syteResult;
    }

    @Override
    void initializeAsync(SyteCallback<AccountDataService> callback) {
        mSyteService.initialize(mConfiguration.getAccountId()).enqueue(
                new Callback<AccountDataService>() {
                    @Override
                    public void onResponse(Call<AccountDataService> call, Response<AccountDataService> response) {
                        SyteResult<AccountDataService> syteResult = new SyteResult<>();
                        syteResult.data = response.body();
                        syteResult.resultCode = response.code();
                        syteResult.isSuccessful = response.isSuccessful();
                        callback.onResult(syteResult);
                    }

                    @Override
                    public void onFailure(Call<AccountDataService> call, Throwable t) {
                        SyteResult<AccountDataService> syteResult = new SyteResult<>();
                        syteResult.data = null;
                        syteResult.isSuccessful = false;
                        callback.onResult(syteResult);
                    }
                }
        );
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
