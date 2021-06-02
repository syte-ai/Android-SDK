package com.syte.ai.android_sdk.data;

import java.util.List;
import java.util.Objects;

public class EventRequestData {

    public String urlReferer;
    public String name;
    public List<String> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRequestData that = (EventRequestData) o;
        return urlReferer.equals(that.urlReferer) &&
                name.equals(that.name) &&
                tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlReferer, name, tags);
    }

}
