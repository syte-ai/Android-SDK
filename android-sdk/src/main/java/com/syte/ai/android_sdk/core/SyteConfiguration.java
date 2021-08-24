package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.SyteStorage;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * SDK configuration
 */
public class SyteConfiguration {

    private final String mAccountId;
    private final String mSignature;

    private String mLocale = "en_US";
    private String mUserId;
    private String mSessionId;

    private SyteStorage mStorage;
    private boolean mAllowAutoCompletionQueue = true;

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
    public String getApiSignature() {
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
        mStorage.addViewedProduct(sessionSku);
    }

    Set<String> getViewedProducts() {
        String viewedProductsString = mStorage.getViewedProducts();
        if (viewedProductsString == null || viewedProductsString.isEmpty()) {
            return new LinkedHashSet<>();
        }
        return new LinkedHashSet<>(Arrays.asList(viewedProductsString.split(",")));
    }

    SyteStorage getStorage() {
        return mStorage;
    }

    /**
     * Indicates whether the calls to {@link com.syte.ai.android_sdk.TextSearchClient#getAutoCompleteAsync(String, String, SyteCallback)}
     * method that are made within 500ms will be saved to queue and invoked.
     * (Only the last call made within 500ms will be saved).
     * If false, the calls made within 500ms will be ignored.
     * @param allowAutoCompletionQueue indicates whether to use the queue
     */
    public void setAllowAutoCompletionQueue(boolean allowAutoCompletionQueue) {
        mAllowAutoCompletionQueue = allowAutoCompletionQueue;
    }

    /**
     * Indicates whether the auto-completion queue is allowed
     * @return is queue allowed.
     */
    public boolean getAllowAutoCompletionQueue() {
        return mAllowAutoCompletionQueue;
    }

}
