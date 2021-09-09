package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.data.result.text_search.Result;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.enums.TextSearchSorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that is used to configure the Text search.
 */
public class TextSearch {

    private String mQuery = "";
    private String mLang = "";
    private final Map<String, List<String>> mFilters = new HashMap<>();
    private int mFrom = 0;
    private int mSize = 30;
    private TextSearchSorting mTextSearchSorting = TextSearchSorting.DEFAULT;
    private Map<String, String> mOptions = new HashMap<>();

    /**
     * @param query text query
     * @param lang locale to retrieve the results for.
     */
    public TextSearch(String query, String lang) {
        mQuery = query;
        mLang = lang;
    }

    /**
     * Add additional query parameters to request.
     * @param key query name
     * @param value query value
     */
    public void addOption(String key, String value) {
        mOptions.put(key, value);
    }

    /**
     * Get additional query parameters.
     * @return additional query parameters
     */
    public Map<String, String> getOptions() {
        return mOptions;
    }

    /**
     * Setter for the text query.
     * @param query text query
     */
    public void setQuery(String query) {
        mQuery = query;
    }

    /**
     * Getter for the text query.
     * @return text query
     */
    public String getQuery() {
        return mQuery;
    }

    /**
     * Setter for the locale.
     * @param lang locale to retrieve the results for.
     */
    public void setLang(String lang) {
        mLang = lang;
    }

    /**
     * Getter for the locale.
     * @return locale
     */
    public String getLang() {
        return mLang;
    }

    /**
     * Add filters to the text search request.
     * Available filters can be retrieved after the first Text search call.
     * To see them use {@link Result#getAggregations()}
     * Example: textSearch.addFilters("Gender", Arrays.asList("female"));
     * @param filterKey Filter name (e.g. "Color")
     * @param filters List of filter values (e.g. ["red", "blue"])
     */
    public void addFilters(String filterKey, List<String> filters) {
        List<String> newFilters = new ArrayList<>(filters);
        mFilters.put(filterKey, newFilters);
    }

    /**
     * Getter for the text search filters.
     * @return text filters.
     */
    public Map<String, List<String>> getFilters() {
        return mFilters;
    }

    /**
     * Set the first page index to return the results from.
     * @param from page index.
     */
    public void setFrom(int from) {
        mFrom = from;
    }

    /**
     * Get index of the initial page.
     * @return index
     */
    public int getFrom() {
        return mFrom;
    }

    /**
     * Set number of pages to return the results from.
     * @param size number of pages.
     */
    public void setSize(int size) {
        mSize = size;
    }

    /**
     * Get number of pages to return the results from.
     * @return number of pages.
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Set sorting order. {@link TextSearchSorting}
     * @param sorting {@link TextSearchSorting}
     */
    public void setSorting(TextSearchSorting sorting) {
        mTextSearchSorting = sorting;
    }

    /**
     * Get sorting order.
     * @return {@link TextSearchSorting}
     */
    public TextSearchSorting getSorting() {
        return mTextSearchSorting;
    }

}