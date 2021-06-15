package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class InitSyte {

    public static synchronized InitSyte getInstance() throws SyteInitializationException {
        InitSyte instance = null;
        try {
            Class<?> clazz = Class.forName("com.syte.ai.android_sdk.internal.InitSyteImpl");
            Method method = clazz.getDeclaredMethod("getInstanceInternal");
            method.setAccessible(true);
            instance = (InitSyte) method.invoke(null);

        } catch (ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException e
        ) {
            throw new SyteInitializationException();
        }

        return instance;
    }

    public abstract SyteResult<AccountDataService> startSession(SyteConfiguration configuration) throws SyteInitializationException;

    public abstract void startSessionAsync(SyteConfiguration configuration, SyteCallback<AccountDataService> callback) throws SyteInitializationException;

    public abstract SyteConfiguration getCurrentConfiguration();

    public abstract void applyConfiguration(SyteConfiguration configuration);

    public abstract AccountDataService getAccountDataService();

    public abstract EventsClient retrieveEventsClient();

    public abstract RecommendationEngineClient retrieveRecommendationEngineClient();

    public abstract ImageSearchClient retrieveImageSearchClient();

    public abstract void endSession();

    public abstract void addSkuPdp(String sku);

}
