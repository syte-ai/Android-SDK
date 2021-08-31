package com.syte.ai.android_sdk.core;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.enums.TextSearchSorting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TextSearchRemoteDataSource extends BaseRemoteDataSource {

    private final SyteService mSyteService;

    TextSearchRemoteDataSource(SyteService syteService, SyteConfiguration configuration) {
        mSyteService = syteService;
        mConfiguration = configuration;
    }

    void getAutoCompleteAsync(
            String query,
            String lang,
            SyteCallback<AutoCompleteResult> callback
    ) {
        generateAutoCompleteCall(query, lang).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onResult(onAutoCompleteResult(response));
                } catch (IOException | JSONException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }
        });
    }

    SyteResult<List<String>> getPopularSearch(String lang) {
        if (!mConfiguration.getStorage().getPopularSearch(lang).isEmpty()) {
            return onPopularSearchResult(mConfiguration.getStorage().getPopularSearch(lang));
        }

        Response<ResponseBody> response = null;
        try {
            response = generatePopularSearchCall(lang).execute();
            SyteResult<List<String>> result = onPopularSearchResult(response);
            mConfiguration.getStorage().addPopularSearch(result.data == null ?
                    new ArrayList<>() : result.data, lang);
            return result;
        } catch (IOException | JSONException e) {
            return handleException(response, e);
        }
    }

    void getPopularSearchAsync(
            String lang,
            SyteCallback<List<String>> callback
    ) {
        if (!mConfiguration.getStorage().getPopularSearch(lang).isEmpty()) {
            callback.onResult(onPopularSearchResult(mConfiguration.getStorage().getPopularSearch(lang)));
            return;
        }

        generatePopularSearchCall(lang).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    SyteResult<List<String>> result = onPopularSearchResult(response);
                    mConfiguration.getStorage().addPopularSearch(result.data == null ?
                            new ArrayList<>() : result.data, lang);
                    callback.onResult(result);
                } catch (IOException | JSONException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }
        });
    }

    public SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch) {
        Response<ResponseBody> response = null;
        try {
            response = generateTextSearchCall(textSearch).execute();
            return onTextSearchResult(textSearch.getQuery(), response);
        } catch (IOException | JSONException e) {
            return handleException(response, e);
        }
    }

    public void getTextSearchAsync(
            TextSearch textSearch,
            SyteCallback<TextSearchResult> callback
    ) {
        generateTextSearchCall(textSearch).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onResult(onTextSearchResult(textSearch.getQuery(), response));
                } catch (IOException | JSONException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }
        });
    }

    private SyteResult<TextSearchResult> onTextSearchResult(
            String query,
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();
        TextSearchResult searchResults = new Gson().fromJson(
                responseString,
                TextSearchResult.class
        );

        if (searchResults.getResult() != null) {
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray hitsArray = jsonObject.getJSONObject("result").getJSONArray("hits");
            for (int i = 0; i < searchResults.getResult().getHits().size(); i++) {
                JSONObject item = hitsArray.getJSONObject(i);
                if (item.has("original_data")) {
                    searchResults
                            .getResult()
                            .getHits()
                            .get(i)
                            .setOriginalData(item.getJSONObject("original_data"));
                }
            }
        }

        SyteResult<TextSearchResult> syteResult = new SyteResult<>();
        syteResult.data = searchResults;
        syteResult.resultCode = result.code();
        syteResult.isSuccessful = result.isSuccessful();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        }

        if (syteResult.isSuccessful &&
                syteResult.data != null &&
                syteResult.data.getResult() != null &&
                syteResult.data.getResult().getExactCount() > 0) {
            mConfiguration.getStorage().addTextSearchTerm(query);
        }

        return syteResult;
    }

    private SyteResult<List<String>> onPopularSearchResult(String popularSearch) {
        SyteResult<List<String>> syteResult = new SyteResult<>();
        syteResult.isSuccessful = true;
        syteResult.resultCode = 200;
        syteResult.data = Arrays.asList(popularSearch.split(","));

        return syteResult;
    }

    private SyteResult<List<String>> onPopularSearchResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();
        String [] searchResults = new Gson().fromJson(
                responseString,
                String[].class
        );

        SyteResult<List<String>> syteResult = new SyteResult<>();
        syteResult.data = Arrays.asList(searchResults);
        syteResult.resultCode = result.code();
        syteResult.isSuccessful = result.isSuccessful();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        }

        return syteResult;
    }

    private SyteResult<AutoCompleteResult> onAutoCompleteResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();
        AutoCompleteResult searchResults = new Gson().fromJson(
                responseString,
                AutoCompleteResult.class
        );

        SyteResult<AutoCompleteResult> syteResult = new SyteResult<>();
        syteResult.data = searchResults;
        syteResult.resultCode = result.code();
        syteResult.isSuccessful = result.isSuccessful();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        }

        return syteResult;
    }

    private Call<ResponseBody> generateTextSearchCall(TextSearch textSearch) {
        return mSyteService.getTextSearch(
                mConfiguration.getAccountId(),
                textSearch.getLang(),
                mConfiguration.getApiSignature(),
                textSearch.getQuery(),
                Utils.generateFiltersString(textSearch.getFilters()),
                textSearch.getFrom(),
                textSearch.getSize(),
                textSearch.getSorting() == TextSearchSorting.DEFAULT ? null :
                        textSearch.getSorting().getName(),
                textSearch.getOptions()
        );
    }

    private Call<ResponseBody> generatePopularSearchCall(String lang) {
        return mSyteService.getPopularSearch(
                mConfiguration.getAccountId(),
                lang,
                mConfiguration.getApiSignature()
        );
    }

    private Call<ResponseBody> generateAutoCompleteCall(String query, String lang) {
        return mSyteService.getAutoComplete(
                mConfiguration.getAccountId(),
                lang,
                query,
                mConfiguration.getApiSignature()
        );
    }

}
