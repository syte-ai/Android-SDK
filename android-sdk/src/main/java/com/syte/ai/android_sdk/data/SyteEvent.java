package com.syte.ai.android_sdk.data;

import java.util.List;
import java.util.Objects;

public class SyteEvent {

    private String mUrlReferer;
    private String mName;
    private List<String> mTags;

    public SyteEvent(String urlReferer, String name, List<String> tags) {
        this.mUrlReferer = urlReferer;
        this.mName = name;
        this.mTags = tags;
    }

    public String getUrlReferer() {
        return mUrlReferer;
    }

    public String getName() {
        return mName;
    }

    public List<String> getTags() {
        return mTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyteEvent that = (SyteEvent) o;
        return mUrlReferer.equals(that.mUrlReferer) &&
                mName.equals(that.mName) &&
                mTags.equals(that.mTags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUrlReferer, mName, mTags);
    }

}
