package com.syte.ai.android_sdk.data;

import android.content.Context;

import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.util.ArrayList;
import java.util.List;

public class SyteConfiguration {

    private final String mAccountId;
    private final String mSignature;

    private String mLocale = "en";
    private List<String> mSessionSkus = new ArrayList<>();
    private String mUserId;
    private String mSessionId;

    private SyteStorage mStorage;

    public SyteConfiguration(Context context, String accountId, String signature) throws SyteInitializationException {
        this.mAccountId = accountId;
        this.mSignature = signature;
        try {
            this.mStorage = new SyteStorage(context);
            this.mSessionId = Long.toString(mStorage.getSessionId());
            this.mUserId = mStorage.getUserId();
        } catch (Exception e) {
            throw new SyteInitializationException();
        }
    }

    public void setLocale(String locale) {
        this.mLocale = locale;
    }

    public String getLocale() {
        return mLocale;
    }

    public String getAccountId() {
        return mAccountId;
    }

    public String getSignature() {
        return mSignature;
    }

    public String getUserId() {
        return mStorage.getUserId();
    }

    public Long getSessionId() {
        return mStorage.getSessionId();
    }

    public void addSessionSku(String sessionSku) {
        mSessionSkus.add(sessionSku);
    }

    public List<String> getSessionSkus() {
        return mSessionSkus;
    }

    public void addSessionSkus(List<String> sessionSkus) {
        this.mSessionSkus.addAll(sessionSkus);
    }

}
