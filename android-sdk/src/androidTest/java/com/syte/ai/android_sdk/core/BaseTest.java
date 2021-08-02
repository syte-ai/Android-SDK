package com.syte.ai.android_sdk.core;

import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BaseTest {

    protected InitSyte mInitSyte;
    protected SyteConfiguration mConfiguration;

    @Before
    public void setUp() {
        mInitSyte = InitSyte.getInstance();
        try {
            mConfiguration = new SyteConfiguration(
                    InstrumentationRegistry.getInstrumentation().getTargetContext(),
                    "9165",
                    "601c206d0a7f780efb9360f3"
            );
        } catch (SyteInitializationException syteInitializationException) {
            fail("Unable to create configuration: " + syteInitializationException.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            mInitSyte.endSession();
        } catch (SyteInitializationException e) {
            // Do nothing, it is fine to catch this exception here since some TC test the 'Not initialized' behavior.
        }
        mConfiguration = null;
    }

    protected void startSessionInternal() {
        try {
            SyteResult<SytePlatformSettings> accountDataService = mInitSyte.startSession(mConfiguration);
            assertNotNull(accountDataService.data);
            assertEquals(accountDataService.resultCode, 200);
            assertTrue(accountDataService.isSuccessful);

            assertEquals(accountDataService.data.getData().getAccountId(), 9165);
        } catch (SyteInitializationException | SyteWrongInputException syteInitializationException) {
            fail("Unable to start session: " + syteInitializationException.getMessage());
        }
    }

}
