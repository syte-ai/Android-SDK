package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;

import java.util.List;

public interface TextSearchClient {

    SyteResult<List<String>> getPopularSearch(String lang);

    void getPopularSearchAsync(String lang, SyteCallback<List<String>> callback);

    SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch);

    void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback);

    void getAutoCompleteAsync(
            String query,
            String lang,
            SyteCallback<AutoCompleteResult> callback
    );

}
