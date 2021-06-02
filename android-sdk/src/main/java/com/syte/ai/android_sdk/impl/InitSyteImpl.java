package com.syte.ai.android_sdk.impl;

import com.syte.ai.android_sdk.EventsClient;
import com.syte.ai.android_sdk.GenericsClient;
import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.data.AccountDataService;
import com.syte.ai.android_sdk.InitSyte;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;

class InitSyteImpl extends InitSyte {

    private SyteConfiguration mConfiguration;

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
    public SyteResult initialize(SyteConfiguration configuration) {
        return null;
    }

    @Override
    public void initializeAsync(SyteConfiguration configuration, SyteCallback callback) {
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
    public GenericsClient retrieveGenericsClient() {
        return new GenericsClientImpl();
    }

    @Override
    public ImageSearchClient retrieveImageSearchClient() {
        return new ImageSearchClientImpl();
    }

    private boolean validateConfig(SyteConfiguration configuration) {
        return true;
    }

}
