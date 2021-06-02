package com.syte.ai.android_sdk.data;

import java.util.ArrayList;
import java.util.List;

public class SyteConfiguration {

    public String accountId;
    public String signature;
    public String locale = "en";

    private List<String> sessionSkus = new ArrayList<>();
    private String userId;
    private String sessionId;

    public SyteConfiguration() {
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

    public void addSessionSkus(List<String> sessionSkus) {
        this.sessionSkus.addAll(sessionSkus);
    }

}
