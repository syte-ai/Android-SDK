package com.syte.ai.android_sdk.events;

import com.syte.ai.android_sdk.enums.EventsTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents Base Syte event. Extend this class and override {@link #getRequestBodyString()}
 * to create a custom event. Example can be found in the sample app.
 */
abstract public class BaseSyteEvent {

    protected transient final String mSyteUrlReferer;
    protected transient final String mName;
    protected transient final List<EventsTag> mEventsTags = new ArrayList<>();

    /**
     * @param name - event name
     * @param syteUrlReferer - Syte URL referer
     * @param tag - event tag
     */
    public BaseSyteEvent(String name, String syteUrlReferer, EventsTag tag) {
        mName = name;
        mSyteUrlReferer = syteUrlReferer;
        mEventsTags.add(tag);
    }

    /**
     * @param name - event name
     * @param syteUrlReferer - Syte URL referer
     * @param tags - list of event tags
     */
    public BaseSyteEvent(String name, String syteUrlReferer, List<EventsTag> tags) {
        mName = name;
        mSyteUrlReferer = syteUrlReferer;
        mEventsTags.addAll(tags);
    }

    /**
     * Getter for request body string
     * @return request body string
     */
    public abstract String getRequestBodyString();

    /**
     * Getter for event name
     * @return event name
     */
    public String getName() {
        return mName;
    }

    /**
     * Getter for list of tags
     * @return list of tags
     */
    public List<EventsTag> getTags() {
        return mEventsTags;
    }

    /**
     * Get list of tags converted to string (comma separated)
     * @return list of tags converted to string
     */
    public String getTagsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(EventsTag tag : mEventsTags) {
            stringBuilder.append(tag.getName());
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    /**
     * Getter for Syte Url referer
     * @return Syte Url referer
     */
    public String getSyteUrlReferer() {
        return mSyteUrlReferer;
    }

}
