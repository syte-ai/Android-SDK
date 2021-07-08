package com.syte.ai.android_sdk.core;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

class SyteStorage {

    private static final String SESSION_ID_PREF_KEY = "syte_session_id_pref";
    private static final String USER_ID_PREF_KEY = "syte_user_id_pref";
    private static final String SESSION_ID_TIMESTAMP_KEY = "syte_session_id_time_pref";

    private final SharedPreferences mSharedPreferences;

    SyteStorage(Context context) throws GeneralSecurityException, IOException {

        MasterKey masterKey = new MasterKey
                .Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        mSharedPreferences = EncryptedSharedPreferences.create(
                context,
                "syte_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

    }

    public Long getSessionId() {
        long sessionId = mSharedPreferences.getLong(SESSION_ID_PREF_KEY, -1);
        if (sessionId == -1 || needNewSessionId()) {
            sessionId = Math.round(Math.random() * 100000000);
            mSharedPreferences
                    .edit()
                    .putLong(SESSION_ID_PREF_KEY, sessionId)
                    .apply();
            renewSessionIdTimestamp();
        }
        return sessionId;
    }

    private boolean needNewSessionId() {
        return System.currentTimeMillis() -
                mSharedPreferences.getLong(SESSION_ID_TIMESTAMP_KEY, 0)
                > 30 * 60 * 1000;
    }

    public void renewSessionIdTimestamp() {
        mSharedPreferences
                .edit()
                .putLong(SESSION_ID_TIMESTAMP_KEY, System.currentTimeMillis())
                .apply();
    }

    public void clearSessionId() {
        mSharedPreferences
                .edit()
                .putLong(SESSION_ID_PREF_KEY, -1)
                .apply();
    }

    public String getUserId() {
        String userId = mSharedPreferences.getString(USER_ID_PREF_KEY, "");
        if (userId.isEmpty()) {
            userId = UUID.randomUUID().toString();
            mSharedPreferences
                    .edit()
                    .putString(USER_ID_PREF_KEY, userId)
                    .apply();
        }
        return userId;
    }

}
