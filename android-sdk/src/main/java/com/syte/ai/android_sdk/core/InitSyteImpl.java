package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventInitialization;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.util.SyteLogger;

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
        InputValidator.validateInput(configuration);
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
        if (mState == SyteState.INITIALIZED) {
            fireEvent(new EventInitialization());
        }
        return result;
    }

    @Override
    public void startSessionAsync(SyteConfiguration configuration, SyteCallback<AccountDataService> callback) throws SyteInitializationException {
        InputValidator.validateInput(configuration);
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
                if (mState == SyteState.INITIALIZED) {
                    fireEvent(new EventInitialization());
                }
                callback.onResult(syteResult);
            }
        });
    }

    @Override
    public SyteConfiguration getConfiguration() {
        verifyInitialized();
        return mConfiguration;
    }

    @Override
    public void setConfiguration(SyteConfiguration configuration) {
        InputValidator.validateInput(configuration);
        verifyInitialized();
        mConfiguration = configuration;
        mRemoteDataSource.setConfiguration(configuration);
        mEventsRemoteDataSource.setConfiguration(configuration);
    }

    @Override
    public AccountDataService getAccountDataService() {
        verifyInitialized();
        return mAccountDataService;
    }

    @Override
    public RecommendationEngineClient retrieveRecommendationEngineClient() {
        verifyInitialized();
        return new RecommendationEngineClientImpl(
                mRemoteDataSource, mAccountDataService
        );
    }

    @Override
    public ImageSearchClient retrieveImageSearchClient() {
        verifyInitialized();
        return new ImageSearchClientImpl(mRemoteDataSource, mAccountDataService);
    }

    @Override
    public void endSession() {
        verifyInitialized();
        mConfiguration.getStorage().clearSessionId();
    }

    @Override
    public void fireEvent(BaseSyteEvent event) {
        verifyInitialized();
        mEventsRemoteDataSource.fireEvent(event);
    }

    @Override
    public void addViewedProduct(String sku) {
        verifyInitialized();
        mConfiguration.addViewedProduct(sku);
    }

    @Override
    public void setLogLevel(SyteLogger.LogLevel logLevel) {
        SyteLogger.setLogLevel(logLevel);
    }

    private void verifyInitialized() throws SyteInitializationException {
        // Disable for now
//        if (mState != SyteState.INITIALIZED) {
//            throw new SyteInitializationException("Syte is not initialized.");
//        }
    }

}
