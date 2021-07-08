package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventInitialization;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

class InitSyteImpl extends InitSyte {

    private enum SyteState {
        IDLE, INITIALIZED
    }

    private SyteConfiguration mConfiguration;
    private SyteRemoteDataSource mRemoteDataSource;
    private AccountDataService mAccountDataService;
    private EventsRemoteDataSource mEventsRemoteDataSource;
    private SyteState mState = SyteState.IDLE;

    InitSyteImpl() {
    }

    @Override
    public SyteResult<AccountDataService> startSession(SyteConfiguration configuration) throws SyteInitializationException {
        if (!validateConfig(configuration)) {
            throw new SyteInitializationException();
        }
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        SyteResult<AccountDataService> result = new SyteResult<>();
        try {
            result = mRemoteDataSource.initialize();
            mAccountDataService = result.data;
            mState = SyteState.INITIALIZED;
        } catch (Exception e) {
            result.isSuccessful = false;
            mState = SyteState.IDLE;
        }
        fireEvent(new EventInitialization());
        return result;
    }

    @Override
    public void startSessionAsync(SyteConfiguration configuration, SyteCallback<AccountDataService> callback) throws SyteInitializationException {
        if (!validateConfig(configuration)) {
            throw new SyteInitializationException();
        }
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        mRemoteDataSource.initializeAsync(new SyteCallback<AccountDataService>() {
            @Override
            public void onResult(SyteResult<AccountDataService> syteResult) {
                if (syteResult.isSuccessful) {
                    mAccountDataService = syteResult.data;
                    mState = SyteState.INITIALIZED;
                } else {
                    mState = SyteState.IDLE;
                }
                fireEvent(new EventInitialization());
                callback.onResult(syteResult);
            }
        });
    }

    @Override
    public SyteConfiguration getCurrentConfiguration() {
        return mConfiguration;
    }

    @Override
    public void applyConfiguration(SyteConfiguration configuration) {
        mConfiguration = configuration;
        mRemoteDataSource.applyConfiguration(configuration);
    }

    @Override
    public AccountDataService getAccountDataService() {
        return mAccountDataService;
    }

    @Override
    public RecommendationEngineClient retrieveRecommendationEngineClient() {
        return new RecommendationEngineClientImpl(
                mRemoteDataSource, mAccountDataService
        );
    }

    @Override
    public ImageSearchClient retrieveImageSearchClient() {
        return new ImageSearchClientImpl(mRemoteDataSource, mAccountDataService);
    }

    @Override
    public void endSession() {
    }

    @Override
    public void fireEvent(BaseSyteEvent event) {
        mEventsRemoteDataSource.fireEvent(event);
    }

    @Override
    public void addSkuPdp(String sku) {
    }

    private boolean validateConfig(SyteConfiguration configuration) {
        return true;
    }

    private boolean checkInitialized() {
        return mState == SyteState.INITIALIZED;
    }

}
