package com.syte.ai.android_sdk.data;

import android.content.Context;

import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.util.ArrayList;
import java.util.List;

public class SyteConfiguration {

    private final String accountId;
    private final String signature;

    private String locale = "en";
    private List<String> sessionSkus = new ArrayList<>();
    private String userId;
    private String sessionId;

    private SyteStorage mStorage;

    public SyteConfiguration(Context context, String accountId, String signature) throws SyteInitializationException {
        this.accountId = accountId;
        this.signature = signature;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSignature() {
        return signature;
    }

    public String getUserId() {
        return "";
    }

    public String getSessionId() {
        return "";
    }

    public void addSessionSku(String sessionSku) {
        sessionSkus.add(sessionSku);
    }

    public List<String> getSessionSkus() {
        return sessionSkus;
    }

    public void addSessionSkus(List<String> sessionSkus) {
        this.sessionSkus.addAll(sessionSkus);
    }

    private void generateSessionId() {

    }

    private void generateUserId() {

    }

}
