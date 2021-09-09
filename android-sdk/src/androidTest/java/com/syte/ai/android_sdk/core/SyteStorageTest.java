package com.syte.ai.android_sdk.core;

import android.app.Instrumentation;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.*;

public class SyteStorageTest {

    private SyteStorage mStorage;

    @Before
    public void setUp() {
        mStorage = new SyteStorage(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void getSessionId() {
        mStorage.clearSessionId();
        assertNotEquals((long) mStorage.getSessionId(), -1L);
    }

    @Test
    public void clearSessionId() {
        long sessionId;
        assertNotEquals(sessionId = mStorage.getSessionId(), -1L);
        mStorage.clearSessionId();
        assertNotEquals(sessionId, (long) mStorage.getSessionId());
    }

    @Test
    public void getUserId() {
        assertNotNull(mStorage.getUserId());
    }

}