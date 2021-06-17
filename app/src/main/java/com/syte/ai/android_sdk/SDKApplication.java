package com.syte.ai.android_sdk;

import android.app.Application;

public class SDKApplication  extends Application {

    private UrlImageSearchManager mUrlImageSearchManager;
    private static SDKApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mUrlImageSearchManager = new UrlImageSearchManager();
        mInstance = this;
    }

    public static SDKApplication getInstance() {
        return mInstance;
    }

    public UrlImageSearchManager getUrlImageSearchManager() {
        return mUrlImageSearchManager;
    }

}
