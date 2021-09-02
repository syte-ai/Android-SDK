package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

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
    public void getPopularSearchAsync() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        startSessionInternal();
        Thread.sleep(1000);
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        mSyte.getPopularSearchAsync("en_US", syteResult -> {
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
        SyteResult<List<String>> syteResult = mSyte.getPopularSearch("en_US");
        assertTrue(syteResult.isSuccessful);
        assertNull(syteResult.errorMessage);
        assertNotNull(syteResult.data);
    }

    @Test
    public void getPopularSearchWrongLang() throws InterruptedException {
        startSessionInternal();
        Thread.sleep(1000);
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        SyteResult<List<String>> syteResult = mSyte.getPopularSearch("some_lang");
        assertNotEquals(mConfiguration.getStorage().getPopularSearch("en_US"), "");
        assertEquals(mConfiguration.getStorage().getPopularSearch("some_lang"), "");
        assertTrue(syteResult.isSuccessful);
        assertNull(syteResult.errorMessage);
        assertNotNull(syteResult.data);
    }

    @Test
    public void getTextSearch() {
        startSessionInternal();
        SyteResult<TextSearchResult> syteResult
                = mSyte.getTextSearch(generateTextSearchRequestData());
        assertTrue(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, 200);
        assertNull(syteResult.errorMessage);
        assertNotNull(syteResult.data);
        assertNotEquals(syteResult.data.getResult().getExactCount(), 0);
        assertNotNull(syteResult.data.getResult().getHits().get(0).getOriginalData());
        assertFalse(mSyte.getRecentTextSearches().isEmpty());
    }

    @Test
    public void getTextSearchNullQuery() {
        startSessionInternal();
        SyteResult<TextSearchResult> syteResult
                = mSyte.getTextSearch(new TextSearch(null, "lang"));
        assertFalse(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, -1);
        assertNotNull(syteResult.errorMessage);
    }

    @Test
    public void getTextSearchAsync() {
        startSessionInternal();
        mSyte.getTextSearchAsync(generateTextSearchRequestData(), syteResult -> {
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
        mSyte.getAutoComplete("a", "en_US", syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertEquals(syteResult.resultCode, 200);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            countDownLatch.countDown();
        });
        mSyte.getAutoComplete("a", "en_US", syteResult -> {
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
        mSyte.setConfiguration(mConfiguration);
        mSyte.getAutoComplete("a", "en_US", syteResult -> {
            assertTrue(syteResult.isSuccessful);
            assertEquals(syteResult.resultCode, 200);
            assertNull(syteResult.errorMessage);
            assertNotNull(syteResult.data);
            countDownLatch.countDown();
        });
        mSyte.getAutoComplete("a", "en_US", syteResult -> {
            fail("Should not be called");
        });
        countDownLatch.await();
    }

    private TextSearch generateTextSearchRequestData() {
        return new TextSearch("shirt", "en_US");
    }

}