package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseSyteEvent {

    protected transient final String mSyteUrlReferer;
    protected transient final String mName;
    protected transient final List<EventsTag> mEventsTags = new ArrayList<>();

    public BaseSyteEvent(String name, String syteUrlReferer, EventsTag tag) {
        mName = name;
        mSyteUrlReferer = syteUrlReferer;
        mEventsTags.add(tag);
    }

    public BaseSyteEvent(String name, String syteUrlReferer, List<EventsTag> tags) {
        mName = name;
        mSyteUrlReferer = syteUrlReferer;
        mEventsTags.addAll(tags);
    }

    public abstract String getRequestBodyString();

    public String getName() {
        return mName;
    }

    public List<EventsTag> getTags() {
        return mEventsTags;
    }

    public String getTagsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(EventsTag tag : mEventsTags) {
            stringBuilder.append(tag.getName());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    public String getSyteUrlReferer() {
        return mSyteUrlReferer;
    }

}
