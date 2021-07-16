package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InitSyteImplTest extends BaseTest {

    @Test
    public void startSession() {
        startSessionInternal();
    }

    @Test
    public void startSessionAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        try {
            mInitSyte.startSessionAsync(mConfiguration, new SyteCallback<AccountDataService>() {
                @Override
                public void onResult(SyteResult<AccountDataService> syteResult) {
                    assertNotNull(syteResult.data);
                    assertEquals(syteResult.resultCode, 200);
                    assertTrue(syteResult.isSuccessful);

                    assertEquals(syteResult.data.getData().getAccountId(), 9165);
                    latch.countDown();
                }
            });
        } catch (SyteInitializationException syteInitializationException) {
            fail("Unable to start session: " + syteInitializationException.getMessage());
        }

        latch.await();

    }

    @Test
    public void getConfiguration() {
        startSessionInternal();
        SyteConfiguration configuration = mInitSyte.getConfiguration();
        assertEquals(configuration.getAccountId(), "9165");
        assertEquals(configuration.getSignature(), "601c206d0a7f780efb9360f3");
        assertNotEquals((long) configuration.getSessionId(), -1L);
        assertNotNull(configuration.getUserId());
    }

    @Test
    public void setConfiguration() {
        startSessionInternal();
        SyteConfiguration configuration = mInitSyte.getConfiguration();
        configuration.setLocale("test_locale");
        mInitSyte.setConfiguration(configuration);

        assertEquals("test_locale", mInitSyte.getConfiguration().getLocale());
    }

    @Test
    public void setConfigurationNotInitialized() {
        try {
            mInitSyte.setConfiguration(mConfiguration);
            fail("Configuration was set successfully while in IDLE state"); // We should not get here
        } catch (SyteInitializationException exception) {
            // We are good
        }
    }

    @Test
    public void retrieveRecommendationEngineClient() {
        startSessionInternal();
        assertNotNull(mInitSyte.retrieveRecommendationEngineClient());
    }

    @Test
    public void retrieveRecommendationEngineClientNotInitialized() {
        try {
            mInitSyte.retrieveRecommendationEngineClient();
            fail("Configuration was set successfully while in IDLE state"); // We should not get here
        } catch (SyteInitializationException exception) {
            // We are good
        }
    }

    @Test
    public void retrieveImageSearchClient() {
        startSessionInternal();
        assertNotNull(mInitSyte.retrieveImageSearchClient());
    }

    @Test
    public void retrieveImageSearchClientNotInitialized() {
        try {
            mInitSyte.retrieveImageSearchClient();
            fail("Configuration was set successfully while in IDLE state"); // We should not get here
        } catch (SyteInitializationException exception) {
            // We are good
        }
    }

    @Test
    public void endSession() {
        startSessionInternal();
        long sessionId = mInitSyte.getConfiguration().getSessionId();
        mInitSyte.endSession();
        startSessionInternal();
        assertNotEquals(sessionId, (long) mInitSyte.getConfiguration().getSessionId());
    }

    @Test
    public void fireEventNotInitialized() {
        try {
            mInitSyte.fireEvent(new BaseSyteEvent("", "", EventsTag.SYTE_ANDROID_SDK) {
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
    public void addViewedProduct() {
        startSessionInternal();
        mInitSyte.addViewedProduct("test");
        assertTrue(mInitSyte.getConfiguration().getViewedProducts().contains("test"));
    }

}