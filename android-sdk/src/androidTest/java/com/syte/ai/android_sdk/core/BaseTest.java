package com.syte.ai.android_sdk.core;

import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BaseTest {

    protected Syte mSyte;
    protected SyteConfiguration mConfiguration;

    @Before
    public void setUp() {
        try {
            mConfiguration = new SyteConfiguration(
                    InstrumentationRegistry.getInstrumentation().getTargetContext(),
                    "9165",
                    "601c206d0a7f780efb9360f3"
            );
            CountDownLatch latch = new CountDownLatch(1);
            Syte.initialize(mConfiguration, syteResult -> {
                mSyte = syteResult.data;
                latch.countDown();
            });
            mConfiguration.getStorage().clearViewedProducts();
            mConfiguration.getStorage().clearPopularSearch();
            mConfiguration.getStorage().clearSessionId();
            latch.await();
        } catch (SyteInitializationException | InterruptedException syteInitializationException) {
            fail("Unable to create configuration: " + syteInitializationException.getMessage());
        }
    }

    protected void startSessionInternal() {}

}
