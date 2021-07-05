package com.syte.ai.android_sdk;

import android.app.Application;

public class SDKApplication  extends Application {

    private SyteManager mSyteManager;
    private static SDKApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mSyteManager = new SyteManager();
        mInstance = this;
    }

    public static SDKApplication getInstance() {
        return mInstance;
    }

    public SyteManager getSyteManager() {
        return mSyteManager;
    }

}
