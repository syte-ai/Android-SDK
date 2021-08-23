package com.syte.ai.android_sdk.data;

import com.syte.ai.android_sdk.enums.TextSearchSorting;

import java.util.ArrayList;
import java.util.List;

public class TextSearch {

    private String mQuery = "";
    private String mLang = "";
    private final List<String> mFilters = new ArrayList<>();
    private int mFrom = 0;
    private int mSize = 20;
    private TextSearchSorting mTextSearchSorting = TextSearchSorting.DEFAULT;

    public TextSearch(String query, String lang) {
        mQuery = query;
        mLang = lang;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setLang(String lang) {
        mLang = lang;
    }

    public String getLang() {
        return mLang;
    }

    public void addFilters(List<String> filters) {
        mFilters.addAll(filters);
    }

    public void addFilter(String filter) {
        mFilters.add(filter);
    }

    public List<String> getFilters() {
        return mFilters;
    }

    public void setFrom(int from) {
        mFrom = from;
    }

    public int getFrom() {
        return mFrom;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public int getSize() {
        return mSize;
    }

    public void setSorting(TextSearchSorting sorting) {
        mTextSearchSorting = sorting;
    }

    public TextSearchSorting getSorting() {
        return mTextSearchSorting;
    }

}