package com.syte.ai.android_sdk;

import android.app.Application;

import com.syte.ai.android_sdk.enums.RecommendationReturnField;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SDKApplication  extends Application {

    private SyteManager mSyteManager;
    private static SDKApplication mInstance;
    private String mLocale = "en_US";
    private Set<String> mSessionSKUs = new HashSet<>();
    private RecommendationReturnField mRecommendationReturnField = RecommendationReturnField.ALL;

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

    public void addSessionSKUs(Set<String> sessionSKUs) {
        mSessionSKUs.addAll(sessionSKUs);
    }

    public Set<String> getSessionSKUs() {
        return mSessionSKUs;
    }

    public void setLocale(String locale) {
        mLocale = locale;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setRecommendationReturnField(RecommendationReturnField field) {
        mRecommendationReturnField = field;
    }

    public RecommendationReturnField getRecommendationReturnField() {
        return mRecommendationReturnField;
    }

}
