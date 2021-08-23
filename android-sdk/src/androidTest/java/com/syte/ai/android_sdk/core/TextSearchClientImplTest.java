package com.syte.ai.android_sdk.core;

import android.app.Instrumentation;

import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.TextSearchClient;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class TextSearchClientImplTest extends BaseTest {

    @Test
    public void testGetPopularSearchLoadedOnInit() throws InterruptedException {
        startSessionInternal();
        Thread.sleep(1000); // Wait till the popular search data is loaded as part of initialization
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
    }

    @Test
    public void testGetPopularSearchDeletedOnEndSession() throws InterruptedException {
        startSessionInternal();
        Thread.sleep(1000);
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        mInitSyte.endSession();
        assertEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
    }

    @Test
    public void getPopularSearchAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        startSessionInternal();
        Thread.sleep(1000);
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        mInitSyte.getTextSearchClient().getPopularSearchAsync("en_US", syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void getPopularSearch() throws InterruptedException {
        startSessionInternal();
        Thread.sleep(1000);
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        SyteResult<List<String>> syteResult = mInitSyte.getTextSearchClient().getPopularSearch("en_US");
        assertTrue(syteResult.isSuccessful);
        assertNull(syteResult.errorMessage);
        assertNotNull(syteResult.data);
    }

    @Test
    public void getPopularSearchWrongLang() throws InterruptedException {
        startSessionInternal();
        Thread.sleep(1000);
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        SyteResult<List<String>> syteResult = mInitSyte.getTextSearchClient().getPopularSearch("some_lang");
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        assertEquals(mConfiguration.getStorage().getPopularSearch("some_lang"), "");
        assertTrue(syteResult.isSuccessful);
        assertNull(syteResult.errorMessage);
        assertNotNull(syteResult.data);
        mInitSyte.endSession();
        assertEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        assertEquals(mConfiguration.getStorage().getPopularSearch("some_lang"), "");
    }

    @Test
    public void getTextSearch() {
        startSessionInternal();
        SyteResult<TextSearchResult> syteResult
                = mInitSyte.getTextSearchClient().getTextSearch(generateTextSearchRequestData());
        assertTrue(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, 200);
        assertNull(syteResult.errorMessage);
        assertNotNull(syteResult.data);
        assertNotEquals(syteResult.data.getResult().getExactCount(), 0);
        assertNotNull(syteResult.data.getResult().getHits().get(0).getOriginalData());
        assertFalse(mInitSyte.getResentTextSearches().isEmpty());
    }

    @Test
    public void getTextSearchNullQuery() {
        startSessionInternal();
        SyteResult<TextSearchResult> syteResult
                = mInitSyte.getTextSearchClient().getTextSearch(new TextSearch(null, "lang"));
        assertFalse(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, -1);
        assertNotNull(syteResult.errorMessage);
    }

    @Test
    public void getTextSearchAsync() {
        startSessionInternal();
        mInitSyte.getTextSearchClient().getTextSearchAsync(generateTextSearchRequestData(), syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertEquals(syteResult.resultCode, 200);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            assertNotEquals(syteResult.data.getResult().getExactCount(), 0);
            assertNotNull(syteResult.data.getResult().getHits().get(0).getOriginalData());
        });
    }

    @Test
    public void getAutoCompleteAsync() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        startSessionInternal();
        mInitSyte.getTextSearchClient().getAutoCompleteAsync("a", "en_US", syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertEquals(syteResult.resultCode, 200);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            countDownLatch.countDown();
        });
        mInitSyte.getTextSearchClient().getAutoCompleteAsync("a", "en_US", syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertEquals(syteResult.resultCode, 200);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            countDownLatch.countDown();
        });
        countDownLatch.await();
    }

    @Test
    public void getAutoCompleteAsyncQueryDisabled() throws InterruptedException, SyteWrongInputException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        startSessionInternal();
        mConfiguration.setAllowAutoCompletionQueue(false);
        mInitSyte.setConfiguration(mConfiguration);
        mInitSyte.getTextSearchClient().getAutoCompleteAsync("a", "en_US", syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertEquals(syteResult.resultCode, 200);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            countDownLatch.countDown();
        });
        mInitSyte.getTextSearchClient().getAutoCompleteAsync("a", "en_US", syteResult -> {
            fail("Should not be called");
        });
        countDownLatch.await();
    }

    private TextSearch generateTextSearchRequestData() {
        return new TextSearch("shirt", "en_US");
    }

}