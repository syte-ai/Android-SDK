package com.syte.ai.android_sdk.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.syte.ai.android_sdk.util.SyteLogger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class SyteStorage {

    private static final String TAG = SyteStorage.class.getSimpleName();

    private static final String SESSION_ID_PREF_KEY = "syte_session_id_pref";
    private static final String USER_ID_PREF_KEY = "syte_user_id_pref";
    private static final String SESSION_ID_TIMESTAMP_KEY = "syte_session_id_time_pref";
    private static final String VIEWED_PRODUCTS_KEY = "syte_viewed_products_pref";
    private static final String POPULAR_SEARCH_KEY = "syte_popular_search_pref";
    private static final String POPULAR_SEARCH_LANG_KEY = "syte_popular_search_lang_pref";
    private static final String TEXT_SEARCH_TERM_KEY = "syte_text_search_term_pref";

    private static final int TEXT_SEARCH_TERM_COUNT = 50;

    @Nullable private SharedPreferences mSharedPreferences = null;

    private boolean mIsEnabled = true;

    SyteStorage(Context context) {

        MasterKey masterKey = null;
        try {
            masterKey = new MasterKey
                    .Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (Exception e) {
            SyteLogger.e(TAG, "Could not create MasterKey instance: " + e.getMessage());
        }

        try {
            if (masterKey != null) {
                mSharedPreferences = EncryptedSharedPreferences.create(
                        context,
                        "syte_shared_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            }
        } catch (Exception e) {
            SyteLogger.e(TAG, "Error creating encrypted shared prefs instance: " + e.getMessage());
        }

        getSessionId();

    }

    public void setEnabled(boolean enabled) {
        mIsEnabled = enabled;

    }

    public Long getSessionId() {
        if (!mIsEnabled) {
            return -1L;
        }
        long sessionId = -1;
        if (mSharedPreferences != null) {
            sessionId = mSharedPreferences.getLong(SESSION_ID_PREF_KEY, -1);
        }
        if (sessionId == -1 || needNewSessionId()) {
            sessionId = Math.round(Math.random() * 100000000);
            if (mSharedPreferences != null) {
                mSharedPreferences
                        .edit()
                        .putLong(SESSION_ID_PREF_KEY, sessionId)
                        .apply();
            }
            renewSessionIdTimestamp();
            clearViewedProducts();
            clearPopularSearch();
        }
        return sessionId;
    }

    private boolean needNewSessionId() {
        long timestamp = 0;
        if (mSharedPreferences != null) {
            timestamp = mSharedPreferences.getLong(SESSION_ID_TIMESTAMP_KEY, 0);
        }
        return System.currentTimeMillis() - timestamp > 30 * 60 * 1000;
    }

    public void renewSessionIdTimestamp() {
        if (mSharedPreferences != null) {
            mSharedPreferences
                    .edit()
                    .putLong(SESSION_ID_TIMESTAMP_KEY, System.currentTimeMillis())
                    .apply();
        }
    }

    public void clearSessionId() {
        if (mSharedPreferences != null) {
            mSharedPreferences
                    .edit()
                    .putLong(SESSION_ID_PREF_KEY, -1)
                    .apply();
            mSharedPreferences
                    .edit()
                    .putLong(SESSION_ID_TIMESTAMP_KEY, 0)
                    .apply();
        }
    }

    public void clearViewedProducts() {
        if (mSharedPreferences != null) {
            mSharedPreferences
                    .edit()
                    .putString(VIEWED_PRODUCTS_KEY, "")
                    .apply();
        }
    }

    public String getUserId() {
        if (!mIsEnabled) {
            return "";
        }
        String userId = "";
        if (mSharedPreferences != null) {
            userId = mSharedPreferences.getString(USER_ID_PREF_KEY, "");
        }
        if (userId.isEmpty()) {
            userId = UUID.randomUUID().toString();
            if (mSharedPreferences != null) {
                mSharedPreferences
                        .edit()
                        .putString(USER_ID_PREF_KEY, userId)
                        .apply();
            }
        }
        return userId;
    }

    public void addViewedProduct(String sessionSku) {
        if (!mIsEnabled) {
            return;
        }
        if (mSharedPreferences != null) {
            String viewedProducts = mSharedPreferences.getString(VIEWED_PRODUCTS_KEY, "");
            StringBuilder newViewedProducts = new StringBuilder();
            if (!viewedProducts.isEmpty()) {
                for (String sku : viewedProducts.split(",")) {
                    if (!sku.equals(sessionSku)) {
                        newViewedProducts.append(sku);
                        newViewedProducts.append(",");
                    }
                }
                newViewedProducts.append(sessionSku);
            }
            mSharedPreferences
                    .edit()
                    .putString(VIEWED_PRODUCTS_KEY, viewedProducts.isEmpty() ?
                            sessionSku : newViewedProducts.toString())
                    .apply();

        }
    }

    public String getViewedProducts() {
        if (!mIsEnabled) {
            return "";
        }
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(VIEWED_PRODUCTS_KEY, "");
        }
        return "";
    }

    public void addPopularSearch(List<String> data, String lang) {
        if (!mIsEnabled) {
            return;
        }

        if (data == null || data.isEmpty()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String item : data) {
            stringBuilder.append(item);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        if (mSharedPreferences != null) {
            mSharedPreferences.edit()
                    .putString(POPULAR_SEARCH_KEY + lang, stringBuilder.toString())
                    .apply();
            String popularSearchLang = mSharedPreferences.getString(POPULAR_SEARCH_LANG_KEY, "");
            mSharedPreferences
                    .edit()
                    .putString(POPULAR_SEARCH_LANG_KEY, popularSearchLang.isEmpty() ?
                            lang : popularSearchLang + "," + lang)
                    .apply();
        }
    }

    public String getPopularSearch(String lang) {
        if (!mIsEnabled) {
            return "";
        }
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(POPULAR_SEARCH_KEY + lang, "");
        }
        return "";
    }

    public void clearPopularSearch() {
        if (mSharedPreferences != null) {
            String langs = mSharedPreferences.getString(POPULAR_SEARCH_LANG_KEY, "");
            if (!langs.isEmpty()) {
                for (String lang : langs.split(",")){
                    mSharedPreferences
                            .edit()
                            .putString(POPULAR_SEARCH_KEY + lang, "")
                            .apply();
                }
                mSharedPreferences.edit()
                        .putString(POPULAR_SEARCH_LANG_KEY, "")
                        .apply();
            }
        }
    }

    public void addTextSearchTerm(String term) {
        if (!mIsEnabled) {
            return;
        }
        if (mSharedPreferences != null) {
            String textSearchTerms = mSharedPreferences.getString(TEXT_SEARCH_TERM_KEY, "");
            if (!textSearchTerms.isEmpty()) {
                if (textSearchTerms.split(",").length >= TEXT_SEARCH_TERM_COUNT) {
                    List<String> termsList = Arrays.asList(textSearchTerms.split(","));
                    termsList.remove(termsList.size() - 1);
                    String resultString = Utils.textSearchTermsString(termsList);
                    textSearchTerms = resultString == null ? "" : resultString;
                }
            }
            mSharedPreferences
                    .edit()
                    .putString(TEXT_SEARCH_TERM_KEY, textSearchTerms.isEmpty() ?
                            term : term + "," + textSearchTerms)
                    .apply();

        }
    }

    public String getTextSearchTerms() {
        if (!mIsEnabled) {
            return "";
        }
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(TEXT_SEARCH_TERM_KEY, "");
        }
        return "";
    }

}
