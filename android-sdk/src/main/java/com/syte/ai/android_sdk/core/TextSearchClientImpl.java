package com.syte.ai.android_sdk.core;

import android.os.Handler;
import android.os.Looper;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.TextSearchClient;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import java.util.List;

class TextSearchClientImpl implements TextSearchClient {

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

    @Override
    public SyteResult<List<String>> getPopularSearch(String lang) {
        try {
            InputValidator.validateInput(lang);
        } catch (SyteWrongInputException e) {
            SyteResult<List<String>> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getPopularSearch(lang);
    }

    @Override
    public void getPopularSearchAsync(String lang, SyteCallback<List<String>> callback) {
        try {
            InputValidator.validateInput(lang);
        } catch (SyteWrongInputException e) {
            SyteResult<List<String>> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }

        mSyteRemoteDataSource.getPopularSearchAsync(lang, syteResult -> {
            if (callback != null) {
                callback.onResult(syteResult);
            }
        });
    }

    @Override
    public SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch) {
        try {
            InputValidator.validateInput(textSearch);
        } catch (SyteWrongInputException e) {
            SyteResult<TextSearchResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getTextSearch(textSearch);
    }

    @Override
    public void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback) {
        try {
            InputValidator.validateInput(textSearch);
        } catch (SyteWrongInputException e) {
            SyteResult<TextSearchResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getTextSearchAsync(textSearch, syteResult -> {
            if (callback != null) {
                callback.onResult(syteResult);
            }
        });
    }

    @Override
    public void getAutoCompleteAsync(
            String query,
            String lang,
            SyteCallback<AutoCompleteResult> callback
    ) {
        try {
            InputValidator.validateInput(query);
            InputValidator.validateInput(lang);
        } catch (SyteWrongInputException e) {
            SyteResult<AutoCompleteResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
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
