package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.TextSearchClient;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventInitialization;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;
import com.syte.ai.android_sdk.util.SyteLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private TextSearchClientImpl mTextSearchClient = null;

    InitSyteImpl() {
    }

    @Override
    public SyteResult<Boolean> startSession(SyteConfiguration configuration) {
        try {
            InputValidator.validateInput(configuration);
        } catch (SyteWrongInputException e) {
            SyteLogger.e(TAG, e.getMessage());
            SyteResult<Boolean> syteResult = new SyteResult<>();
            syteResult.data = false;
            syteResult.errorMessage = e.getMessage();
            return syteResult;
        }
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        SyteResult<Boolean> result = new SyteResult<>();
        SyteResult<SytePlatformSettings> responseResult = new SyteResult<>();
        try {
            responseResult = mRemoteDataSource.initialize();
            mSytePlatformSettings = responseResult.data;
            result.data = responseResult.isSuccessful;
            result.isSuccessful = responseResult.isSuccessful;
            result.errorMessage = responseResult.errorMessage;
            mState = SyteState.INITIALIZED;
        } catch (Exception e) {
            result.data = false;
            result.isSuccessful = false;
            result.errorMessage = e.getMessage();
            mState = SyteState.IDLE;
        }
        result.resultCode = responseResult.resultCode;
        if (mState == SyteState.INITIALIZED) {
            fireEvent(new EventInitialization());
            getTextSearchClient().getPopularSearchAsync(mConfiguration.getLocale(), syteResult -> {
                if (syteResult.isSuccessful && syteResult.data != null) {
                    mConfiguration.getStorage().addPopularSearch(syteResult.data);
                }
            });
        }
        return result;
    }

    @Override
    public void startSessionAsync(SyteConfiguration configuration, SyteCallback<Boolean> callback) {
        try {
            InputValidator.validateInput(configuration);
        } catch (SyteWrongInputException e) {
            SyteLogger.e(TAG, e.getMessage());
            SyteResult<Boolean> syteResult = new SyteResult<>();
            syteResult.data = false;
            syteResult.errorMessage = e.getMessage();
            callback.onResult(syteResult);
            return;
        }
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        mRemoteDataSource.initializeAsync(syteResult -> {
            if (syteResult.isSuccessful) {
                mSytePlatformSettings = syteResult.data;
                mState = SyteState.INITIALIZED;
            } else {
                mState = SyteState.IDLE;
            }
            if (mState == SyteState.INITIALIZED) {
                fireEvent(new EventInitialization());
                getTextSearchClient().getPopularSearchAsync(mConfiguration.getLocale(), result -> {
                    if (result.isSuccessful && result.data != null) {
                        mConfiguration.getStorage().addPopularSearch(result.data);
                    }
                });
            }
            if (callback != null) {
                SyteResult<Boolean> result = new SyteResult<>();
                result.data = syteResult.isSuccessful;
                result.resultCode = syteResult.resultCode;
                result.isSuccessful = syteResult.isSuccessful;
                result.errorMessage = syteResult.errorMessage;
                callback.onResult(result);
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
        mTextSearchClient.setAllowAutoCompletionQueue(configuration.getAllowAutoCompletionQueue());
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
    public TextSearchClient getTextSearchClient() {
        verifyInitialized();
        if (mTextSearchClient == null) {
            mTextSearchClient = new TextSearchClientImpl(
                    mRemoteDataSource,
                    mConfiguration.getAllowAutoCompletionQueue()
            );
        }

        return mTextSearchClient;
    }

    @Override
    public void endSession() {
        verifyInitialized();
        mConfiguration.getStorage().clearSessionId();
        mConfiguration.getStorage().clearViewedProducts();
        mConfiguration.getStorage().clearPopularSearch();
        mConfiguration = null;
    }

    @Override
    public void fireEvent(BaseSyteEvent event) {
        verifyInitialized();
        mEventsRemoteDataSource.fireEvent(event);
    }

    @Override
    public void addViewedItem(String sku) throws SyteWrongInputException {
        verifyInitialized();
        InputValidator.validateInput(sku);
        mConfiguration.addViewedProduct(sku);
    }

    @Override
    public Set<String> getViewedProducts() {
        if (mConfiguration != null) {
            return mConfiguration.getViewedProducts();
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public List<String> getResentTextSearches() {
        if (mConfiguration != null) {
            String searchTerms = mConfiguration.getStorage().getTextSearchTerms();
            if (!searchTerms.isEmpty()) {
                return Arrays.asList(searchTerms.split(","));
            }
        }

        return new ArrayList<>();
    }

    @Override
    public void setLogLevel(SyteLogger.LogLevel logLevel) {
        SyteLogger.setLogLevel(logLevel);
    }

    private void verifyInitialized() throws SyteInitializationException {
        if (mState != SyteState.INITIALIZED) {
            throw new SyteInitializationException("Syte is not initialized.");
        }
    }

}
