package com.syte.ai.android_sdk.core;

import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
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
    public void startSessionConfigurationNull() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Syte.initialize(null, result -> {
            assertNotNull(result);
            assertNotNull(result.errorMessage);
            assertNull(result.data);
            assertFalse(result.isSuccessful);
            assertEquals(result.resultCode, -1);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void startSessionAccountIdNull() throws InterruptedException {
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                null,
                ""
        );
        CountDownLatch latch = new CountDownLatch(1);
        Syte.initialize(null, result -> {
            assertNotNull(result);
            assertNotNull(result.errorMessage);
            assertNull(result.data);
            assertFalse(result.isSuccessful);
            assertEquals(result.resultCode, -1);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void startSessionSignatureNull() throws InterruptedException {
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                "test",
                null
        );
        CountDownLatch latch = new CountDownLatch(1);
        Syte.initialize(null, result -> {
            assertNotNull(result);
            assertNotNull(result.errorMessage);
            assertNull(result.data);
            assertFalse(result.isSuccessful);
            assertEquals(result.resultCode, -1);
            latch.countDown();
        });
        latch.await();
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
    public void setConfigurationNotInitialized() {
        try {
            try {
                mSyte.setConfiguration(mConfiguration);
            } catch (SyteWrongInputException e) {
                e.printStackTrace();
            }
            fail("Configuration was set successfully while in IDLE state"); // We should not get here
        } catch (SyteInitializationException exception) {
            // We are good
        }
    }

    @Test
    public void fireEventNotInitialized() {
        try {
            mSyte.fireEvent(new BaseSyteEvent("", "", EventsTag.SYTE_ANDROID_SDK) {
                @Override
                public String getRequestBodyString() {
                    return null;
                }
            });
            fail("Configuration was set successfully while in IDLE state"); // We should not get here
        } catch (SyteInitializationException exception) {
            // We are good
        }
    }

    @Test
    public void addViewedProduct() throws SyteWrongInputException {
        startSessionInternal();
        mSyte.addViewedItem("test");
        assertTrue(mSyte.getConfiguration().getViewedProducts().contains("test"));
    }

}