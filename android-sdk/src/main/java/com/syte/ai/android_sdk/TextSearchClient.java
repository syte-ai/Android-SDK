package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;

import java.util.List;

/**
 * Class responsible for Text Search functionality.
 */
public interface TextSearchClient {

    /**
     * Retrieves the most popular searches for the specified language.
     * Synchronous method. Must NOT be called on the UI thread.
     * @param lang locale to retrieve the searches for.
     * @return The list of the most popular searches.
     */
    SyteResult<List<String>> getPopularSearch(String lang);

    /**
     * Retrieves the most popular searches for the specified language.
     * Asynchronous equivalent of the {@link #getPopularSearch(String)} method.
     * @param lang locale to retrieve the searches for.
     * @param callback instance of {@link SyteCallback}.
     */
    void getPopularSearchAsync(String lang, SyteCallback<List<String>> callback);

    /**
     * Retrieves the text search results.
     * Synchronous method. Must NOT be called on the UI thread.
     * @param textSearch query parameters {@link TextSearch}
     * @return {@link SyteResult}
     */
    SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch);

    /**
     * Retrieves the text search results.
     * Asynchronous equivalent of the {@link #getTextSearch(TextSearch)} method.
     * @param textSearch query parameters {@link TextSearch}
     * @param callback {@link SyteResult}
     */
    void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback);

    /**
     * Retrieves Auto-complete results.
     * This method is asynchronous.
     * There must be at least 500ms interval between calls to this method.
     * Otherwise there are two options:
     * 1. If {@link com.syte.ai.android_sdk.core.SyteConfiguration#setAllowAutoCompletionQueue(boolean)}
     *    is set to true, the last request data will be saved in a queue and the
     *    request will be fired after 500ms.
     * 2. If {@link com.syte.ai.android_sdk.core.SyteConfiguration#setAllowAutoCompletionQueue(boolean)}
     *    is set to false, the calls to this method made within 500ms will be ignored.
     * @param query text query
     * @param lang locale to retrieve the searches for.
     * @param callback {@link SyteCallback}
     */
    void getAutoCompleteAsync(
            String query,
            String lang,
            SyteCallback<AutoCompleteResult> callback
    );

}
