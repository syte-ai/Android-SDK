package com.syte.ai.android_sdk.app;

import android.app.Application;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.HashSet;
import java.util.Set;

public class SDKApplication  extends Application {

    private SyteManager mSyteManager;
    private static SDKApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mSyteManager = new SyteManager(this);
        mInstance = this;
    }

    public static SDKApplication getInstance() {
        return mInstance;
    }

    public SyteManager getSyteManager() {
        return mSyteManager;
    }

}
