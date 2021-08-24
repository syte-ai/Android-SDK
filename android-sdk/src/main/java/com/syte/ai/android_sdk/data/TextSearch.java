package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.TextSearchSorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is used to configure the Text search.
 */
public class TextSearch {

    private String mQuery = "";
    private String mLang = "";
    private final List<String> mFilters = new ArrayList<>();
    private int mFrom = 0;
    private int mSize = 20;
    private TextSearchSorting mTextSearchSorting = TextSearchSorting.DEFAULT;

    /**
     * @param query text query
     * @param lang locale to retrieve the results for.
     */
    public TextSearch(String query, String lang) {
        mQuery = query;
        mLang = lang;
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
     * Setter for the text filters.
     * @param filters list of text filters.
     */
    public void addFilters(List<String> filters) {
        mFilters.addAll(filters);
    }

    /**
     * Setter for the text filters.
     * @param filter text filter.
     */
    public void addFilter(String filter) {
        mFilters.add(filter);
    }

    /**
     * Getter for the text filters.
     * @return text filters.
     */
    public List<String> getFilters() {
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