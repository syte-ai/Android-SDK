package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.AccountDataService;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public abstract class InitSyte {

    public static synchronized InitSyte getInstance() throws SyteInitializationException {
        InitSyte instance = null;
        try {
            Class<?> clazz = Class.forName("com.syte.ai.android_sdk.impl.InitSyteImpl");
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

    public abstract SyteResult initialize(SyteConfiguration configuration);

    public abstract void initializeAsync(SyteConfiguration configuration, SyteCallback callback);

    public abstract SyteConfiguration getCurrentConfiguration();

    public abstract void applyConfiguration(SyteConfiguration configuration);

    public abstract void applyConfigurationAsync(SyteConfiguration configuration, SyteCallback callback);

    public abstract AccountDataService getAccountDataService();

    public abstract EventsClient retrieveEventsClient();

    public abstract RecommendationEngineClient retrieveRecommendationEngineClient();

    public abstract GenericsClient retrieveGenericsClient();

    public abstract ImageSearchClient retrieveImageSearchClient();

}
