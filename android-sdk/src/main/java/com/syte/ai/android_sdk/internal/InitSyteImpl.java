package com.syte.ai.android_sdk.internal;

import com.syte.ai.android_sdk.EventsClient;
import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.data.AccountDataService;
import com.syte.ai.android_sdk.InitSyte;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

class InitSyteImpl extends InitSyte {

    private enum SyteState {
        IDLE, INITIALIZED
    }

    private SyteConfiguration mConfiguration;
    private SyteRemoteDataSource mRemoteDataSource;
    private SyteState mState = SyteState.IDLE;

    private static InitSyteImpl mInstance = null;

    private InitSyteImpl() {
    }

    static synchronized InitSyteImpl getInstanceInternal() {
        if (mInstance == null) {
            mInstance = new InitSyteImpl();
        }

        return mInstance;
    }

    @Override
    public SyteResult startSession(SyteConfiguration configuration) throws SyteInitializationException {
        if(!validateConfig(configuration)) {
            throw new SyteInitializationException();
        }
        mState = SyteState.INITIALIZED;
        mConfiguration = configuration;
        return null;
    }

    @Override
    public void startSessionAsync(SyteConfiguration configuration, SyteCallback callback) {
    }

    @Override
    public SyteConfiguration getCurrentConfiguration() {
        return null;
    }

    @Override
    public void applyConfiguration(SyteConfiguration configuration) {
    }

    @Override
    public void applyConfigurationAsync(SyteConfiguration configuration, SyteCallback callback) {
    }

    @Override
    public AccountDataService getAccountDataService() {
        return null;
    }

    @Override
    public EventsClient retrieveEventsClient() {
        return new EventsClientImpl();
    }

    @Override
    public RecommendationEngineClient retrieveRecommendationEngineClient() {
        return new RecommendationEngineClientImpl();
    }

    @Override
    public ImageSearchClient retrieveImageSearchClient() {
        return new ImageSearchClientImpl();
    }

    @Override
    public void endSession() { }

    @Override
    public void addSkuPdp(String sku) { }

    private boolean validateConfig(SyteConfiguration configuration) {
        return true;
    }

    private boolean checkInitialized() {
        return mState == SyteState.INITIALIZED;
    }

}
