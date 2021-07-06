package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;


public abstract class InitSyte {

    public static synchronized InitSyte getInstance() {
        return new InitSyteImpl();
    }

    public abstract SyteResult<AccountDataService> startSession(SyteConfiguration configuration) throws SyteInitializationException;

    public abstract void startSessionAsync(SyteConfiguration configuration, SyteCallback<AccountDataService> callback) throws SyteInitializationException;

    public abstract SyteConfiguration getCurrentConfiguration();

    public abstract void applyConfiguration(SyteConfiguration configuration);

    public abstract AccountDataService getAccountDataService();

    public abstract RecommendationEngineClient retrieveRecommendationEngineClient();

    public abstract ImageSearchClient retrieveImageSearchClient();

    public abstract void endSession();

    public abstract void fireEvent(BaseSyteEvent event);

    public abstract void addSkuPdp(String sku);

}