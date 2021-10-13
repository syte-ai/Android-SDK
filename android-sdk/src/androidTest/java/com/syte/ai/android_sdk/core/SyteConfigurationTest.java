package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import org.junit.Test;

import static org.junit.Assert.*;

public class SyteConfigurationTest extends BaseTest {

    @Test
    public void getSessionSkusString() throws SyteWrongInputException {
        startSessionInternal();
        mSyte.addViewedProduct("test1");
        mSyte.addViewedProduct("test2");
        mSyte.addViewedProduct("test3");
        assertEquals("test1,test2,test3", Utils.viewedProductsString(mConfiguration.getViewedProducts()));
    }

    @Test
    public void getSessionSkusStringSimilarInput() throws SyteWrongInputException {
        startSessionInternal();
        mSyte.addViewedProduct("test1");
        mSyte.addViewedProduct("test2");
        mSyte.addViewedProduct("test1");
        assertEquals("test2,test1", Utils.viewedProductsString(mConfiguration.getViewedProducts()));
    }

    @Test
    public void getSessionSkusJSONArray() throws SyteWrongInputException {
        startSessionInternal();
        mSyte.addViewedProduct("test1");
        mSyte.addViewedProduct("test2");
        mSyte.addViewedProduct("test3");
        assertEquals("[\"test1\",\"test2\",\"test3\"]", Utils.viewedProductsJSONArray(mConfiguration.getViewedProducts()));
    }

}