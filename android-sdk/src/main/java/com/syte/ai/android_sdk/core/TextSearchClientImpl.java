package com.syte.ai.android_sdk.core;

import android.os.Handler;
import android.os.Looper;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import java.util.List;

class TextSearchClientImpl {

    private static class AutoCompleteRequest {
        private String mQuery = null;
        private String mLang = null;
        private SyteCallback<AutoCompleteResult> mCallback = null;

        void setData(String query, String lang, SyteCallback<AutoCompleteResult> callback) {
            mQuery = query;
            mLang = lang;
            mCallback = callback;
        }

        String getLang() { return mLang; }

        String getQuery() { return mQuery; }

        SyteCallback<AutoCompleteResult> getCallback() { return mCallback; }

        void cleanUp() {
            mQuery = null;
            mLang = null;
            mCallback = null;
        }

        boolean isSet() {
            return mQuery != null && mLang != null && mCallback != null;
        }

    }

    private static final int AUTO_COMPLETE_INTERVAL_MS = 500;

    private final SyteRemoteDataSource mSyteRemoteDataSource;
    private final AutoCompleteRequest mNextQuery = new AutoCompleteRequest();
    private boolean mIsAutoCompleteAvailable = true;
    private boolean mAllowAutoCompletionQueue;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mIsAutoCompleteAvailable = true;
            if (mNextQuery.isSet()) {
                getAutoCompleteAsync(mNextQuery.mQuery, mNextQuery.mLang, mNextQuery.mCallback);
                mNextQuery.cleanUp();
            }
        }
    };

    TextSearchClientImpl(SyteRemoteDataSource syteRemoteDataSource, boolean allowAutoCompletionQueue) {
        mSyteRemoteDataSource = syteRemoteDataSource;
        mAllowAutoCompletionQueue = allowAutoCompletionQueue;
    }

    public SyteResult<List<String>> getPopularSearch(String lang) {
        try {
            InputValidator.validateInput(lang);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getPopularSearch(lang);
    }

    public void getPopularSearchAsync(String lang, SyteCallback<List<String>> callback) {
        try {
            InputValidator.validateInput(lang);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }

        mSyteRemoteDataSource.getPopularSearchAsync(lang, syteResult -> {
            if (callback != null) {
                callback.onResult(syteResult);
            }
        });
    }

    public SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch) {
        try {
            InputValidator.validateInput(textSearch);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getTextSearch(textSearch);
    }

    public void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback) {
        try {
            InputValidator.validateInput(textSearch);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }
        mSyteRemoteDataSource.getTextSearchAsync(textSearch, syteResult -> {
            if (callback != null) {
                callback.onResult(syteResult);
            }
        });
    }

    public void getAutoCompleteAsync(
            String query,
            String lang,
            SyteCallback<AutoCompleteResult> callback
    ) {
        try {
            InputValidator.validateInput(query);
            InputValidator.validateInput(lang);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }
        if (mIsAutoCompleteAvailable) {
            mIsAutoCompleteAvailable = false;
            mSyteRemoteDataSource.getAutoCompleteAsync(
                    query,
                    lang,
                    syteResult -> {
                        if (callback != null) {
                            callback.onResult(syteResult);
                        }
                        new Handler(Looper.getMainLooper())
                                .postDelayed(mRunnable, AUTO_COMPLETE_INTERVAL_MS);
                    });
        } else if (mAllowAutoCompletionQueue) {
            mNextQuery.setData(query, lang, callback);
        }
    }

    void setAllowAutoCompletionQueue(boolean allowAutoCompletionQueue) {
        mAllowAutoCompletionQueue = allowAutoCompletionQueue;
    }

}
