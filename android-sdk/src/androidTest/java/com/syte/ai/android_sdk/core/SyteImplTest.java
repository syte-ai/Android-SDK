package com.syte.ai.android_sdk.core;

import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SyteImplTest extends BaseTest {

    @Test
    public void startSession() {
        startSessionInternal();
    }

    @Test
    public void createInstanceConfigurationNull() throws InterruptedException {
        try {
            Syte.newInstance(null);
        } catch (SyteInitializationException e) {
            // We should get here
            return;
        }
        fail("Unexpected success");
    }

    @Test
    public void createInstanceAccountIdNull() throws InterruptedException {
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                null,
                ""
        );
        try {
            Syte.newInstance(syteConfiguration);
        } catch (SyteInitializationException e) {
            // We should get here
            return;
        }

        fail("Unexpected success");
    }

    @Test
    public void createInstanceSignatureNull() throws InterruptedException {
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                "test",
                null
        );
        try {
            Syte.newInstance(syteConfiguration);
        } catch (SyteInitializationException e) {
            // We should get here
            return;
        }

        fail("Unexpected success");

    }

    @Test
    public void getConfiguration() {
        startSessionInternal();
        SyteConfiguration configuration = mSyte.getConfiguration();
        assertEquals(configuration.getAccountId(), "9165");
        assertEquals(configuration.getApiSignature(), "601c206d0a7f780efb9360f3");
        assertNotEquals((long) configuration.getSessionId(), -1L);
        assertNotNull(configuration.getUserId());
    }

    @Test
    public void setConfiguration() {
        startSessionInternal();
        SyteConfiguration configuration = mSyte.getConfiguration();
        configuration.setLocale("test_locale");
        try {
            mSyte.setConfiguration(configuration);
        } catch (SyteWrongInputException e) {
            e.printStackTrace();
        }

        assertEquals("test_locale", mSyte.getConfiguration().getLocale());
    }

    @Test
    public void addViewedProduct() throws SyteWrongInputException {
        startSessionInternal();
        mSyte.addViewedItem("test");
        assertTrue(mSyte.getConfiguration().getViewedProducts().contains("test"));
    }

}