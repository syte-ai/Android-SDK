package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.core.SyteStorage;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * SDK configuration
 */
public class SyteConfiguration {

    private final String mAccountId;
    private final String mSignature;

    private String mLocale = "en_Us";
    private List<String> mSessionSkus = new ArrayList<>();
    private String mUserId;
    private String mSessionId;

    private SyteStorage mStorage;

    /**
     *
     * @param context context
     * @param accountId contact Syte for this value
     * @param signature contact Syte for this value
     */
    public SyteConfiguration(Context context, String accountId, String signature) {
        this.mAccountId = accountId;
        this.mSignature = signature;
        this.mStorage = new SyteStorage(context);
        this.mSessionId = Long.toString(mStorage.getSessionId());
        this.mUserId = mStorage.getUserId();
    }

    /**
     * Method to set locale. Will be used in requests.
     * @param locale locale to use
     */
    public void setLocale(String locale) {
        this.mLocale = locale;
    }

    /**
     * Getter for locale
     * @return locale
     */
    public String getLocale() {
        return mLocale;
    }

    /**
     * Getter for account ID
     * @return account ID
     */
    public String getAccountId() {
        return mAccountId;
    }

    /**
     * Getter for signature
     * @return signature
     */
    public String getSignature() {
        return mSignature;
    }

    /**
     * Getter for user ID
     * @return user ID. This value is generated automatically.
     */
    public String getUserId() {
        return mStorage.getUserId();
    }

    /**
     * Getter for session ID
     * @return session ID. This value is generated automatically.
     */
    public Long getSessionId() {
        return mStorage.getSessionId();
    }

    void addViewedProduct(String sessionSku) {
        mSessionSkus.add(sessionSku);
    }

    void addViewedProducts(List<String> sessionSkus) {
        this.mSessionSkus.addAll(sessionSkus);
    }

    List<String> getViewedProducts() {
        return mSessionSkus;
    }

    @Nullable
    String getSessionSkusString() {
        if (mSessionSkus.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String sku : mSessionSkus) {
            stringBuilder.append(sku);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    @Nullable
    String getSessionSkusJSONArray() {
        if (mSessionSkus.isEmpty()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (String sku : mSessionSkus) {
            stringBuilder.append("\"");
            stringBuilder.append(sku);
            stringBuilder.append("\"");
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    SyteStorage getStorage() {
        return mStorage;
    }

}
