package com.syte.ai.android_sdk.core;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SyteConfigurationTest extends BaseTest {

    @Test
    public void getSessionSkusString() {
        startSessionInternal();
        mInitSyte.addViewedProduct("test1");
        mInitSyte.addViewedProduct("test2");
        mInitSyte.addViewedProduct("test3");
        assertEquals("test1,test2,test3", mConfiguration.getSessionSkusString());
    }

    @Test
    public void getSessionSkusJSONArray() {
        startSessionInternal();
        mInitSyte.addViewedProduct("test1");
        mInitSyte.addViewedProduct("test2");
        mInitSyte.addViewedProduct("test3");
        assertEquals("[\"test1\",\"test2\",\"test3\"]", mConfiguration.getSessionSkusJSONArray());
    }

}