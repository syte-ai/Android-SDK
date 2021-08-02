package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventInitialization;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;
import com.syte.ai.android_sdk.util.SyteLogger;

class InitSyteImpl extends InitSyte {

    private static final String TAG = "InitSyte";

    private enum SyteState {
        IDLE, INITIALIZED
    }

    private SyteConfiguration mConfiguration;
    private SyteRemoteDataSource mRemoteDataSource;
    private SytePlatformSettings mSytePlatformSettings;
    private EventsRemoteDataSource mEventsRemoteDataSource;
    private SyteState mState = SyteState.IDLE;

    InitSyteImpl() {
    }

    @Override
    public SyteResult<SytePlatformSettings> startSession(SyteConfiguration configuration) throws SyteWrongInputException {
        InputValidator.validateInput(configuration);
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        SyteResult<SytePlatformSettings> result = new SyteResult<>();
        try {
            result = mRemoteDataSource.initialize();
            mSytePlatformSettings = result.data;
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
    public void startSessionAsync(SyteConfiguration configuration, SyteCallback<SytePlatformSettings> callback) throws SyteInitializationException {
        try {
            InputValidator.validateInput(configuration);
        } catch (SyteWrongInputException e) {
            SyteLogger.e(TAG, e.getMessage());
            SyteResult<SytePlatformSettings> syteResult = new SyteResult<>();
            syteResult.errorMessage = e.getMessage();
            callback.onResult(syteResult);
        }
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        mRemoteDataSource.initializeAsync(new SyteCallback<SytePlatformSettings>() {
            @Override
            public void onResult(SyteResult<SytePlatformSettings> syteResult) {
                if (syteResult.isSuccessful) {
                    mSytePlatformSettings = syteResult.data;
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
        return mConfiguration;
    }

    @Override
    public void setConfiguration(SyteConfiguration configuration) throws SyteWrongInputException {
        InputValidator.validateInput(configuration);
        verifyInitialized();
        mConfiguration = configuration;
        mRemoteDataSource.setConfiguration(configuration);
        mEventsRemoteDataSource.setConfiguration(configuration);
    }

    @Override
    public SytePlatformSettings getSytePlatformSettings() {
        verifyInitialized();
        return mSytePlatformSettings;
    }

    @Override
    public ProductRecommendationClient getProductRecommendationClient() {
        verifyInitialized();
        return new ProductRecommendationClientImpl(
                mRemoteDataSource, mSytePlatformSettings
        );
    }

    @Override
    public ImageSearchClient getImageSearchClient() {
        verifyInitialized();
        return new ImageSearchClientImpl(mRemoteDataSource, mSytePlatformSettings);
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
//        if (mState != SyteState.INITIALIZED) {
//            throw new SyteInitializationException("Syte is not initialized.");
//        }
    }

}
