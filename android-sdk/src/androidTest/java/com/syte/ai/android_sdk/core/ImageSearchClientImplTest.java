package com.syte.ai.android_sdk.core;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.ItemsResult;
import com.syte.ai.android_sdk.enums.SyteProductType;
import com.syte.ai.android_sdk.exceptions.SyteGeneralException;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class ImageSearchClientImplTest extends BaseTest {

    private UrlImageSearch createUrlImageSearchRequestData() {
        UrlImageSearch requestData = new UrlImageSearch(
                "https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg",
                SyteProductType.DISCOVERY_BUTTON
        );
        requestData.setSku("13705596");
        return requestData;
    }

    @Test
    public void getBounds() {
        startSessionInternal();
        SyteResult<BoundsResult> result = getBoundsInternal(null);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);

        assertEquals(result.data.getBounds().size(), 4);
        assertNotNull(result.data.getItemsForFirstBound());
    }

    @Test
    public void getBoundsImageUrlNull() {
        startSessionInternal();
        UrlImageSearch requestData = new UrlImageSearch(null, SyteProductType.CAMERA);

        SyteResult<BoundsResult> result = getBoundsInternal(requestData);
        assertNotNull(result);
        assertNull(result.data);
        assertNotNull(result.errorMessage);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getBoundsProductTypeNull() {
        startSessionInternal();
        UrlImageSearch requestData = new UrlImageSearch("test", null);

        SyteResult<BoundsResult> result = getBoundsInternal(requestData);
        assertNotNull(result);
        assertNull(result.data);
        assertNotNull(result.errorMessage);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getBoundsNoSku() {
        startSessionInternal();

        UrlImageSearch requestData = new UrlImageSearch(
                "https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg",
                SyteProductType.DISCOVERY_BUTTON
        );

        SyteResult<BoundsResult> result = getBoundsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);

        assertEquals(result.data.getBounds().size(), 4);
    }

    @Test
    public void getBoundsFirstBoundOffersDisabled() {
        startSessionInternal();

        UrlImageSearch requestData = createUrlImageSearchRequestData();
        requestData.setRetrieveItemsForTheFirstBound(false);

        SyteResult<BoundsResult> result = getBoundsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);

        assertEquals(result.data.getBounds().size(), 4);
        assertNull(result.data.getItemsForFirstBound());
    }

    @Test
    public void getOffers() {
        startSessionInternal();
        SyteResult<BoundsResult> boundsResult = getBoundsInternal(null);
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        SyteResult<ItemsResult> offersResult = client.getItemsForBound(boundsResult.data.getBounds().get(1), null);
        assertNotNull(offersResult);
        assertNotNull(offersResult.data);
        assertEquals(offersResult.resultCode, 200);
        assertTrue(offersResult.isSuccessful);
        assertFalse(offersResult.data.getItems().isEmpty());
    }

    @Test
    public void getOffersBoundNull() {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        SyteResult<ItemsResult> offersResult = client.getItemsForBound(null, null);
        assertNotNull(offersResult);
        assertNotNull(offersResult.errorMessage);
        assertNull(offersResult.data);
        assertEquals(offersResult.resultCode, -1);
        assertFalse(offersResult.isSuccessful);
    }

    @Test
    public void getOffersWithCrop() {
        startSessionInternal();
        SyteResult<BoundsResult> boundsResult = getBoundsInternal(null);
        ImageSearchClient client = mInitSyte.getImageSearchClient();

        CropCoordinates coordinates = new CropCoordinates(
                0,
                0,
                1,
                1
        );

        SyteResult<ItemsResult> offersResult = client.getItemsForBound(boundsResult.data.getBounds().get(1), coordinates);
        assertNotNull(offersResult);
        assertNotNull(offersResult.data);
        assertEquals(offersResult.resultCode, 200);
        assertTrue(offersResult.isSuccessful);
        assertFalse(offersResult.data.getItems().isEmpty());
    }

    @Test
    public void getOffersAsync() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        SyteResult<BoundsResult> boundsResult = getBoundsInternal(null);
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        client.getItemsForBoundAsync(boundsResult.data.getBounds().get(1), null, new SyteCallback<ItemsResult>() {
            @Override
            public void onResult(SyteResult<ItemsResult> offersResult) {
                assertNotNull(offersResult);
                assertNotNull(offersResult.data);
                assertEquals(offersResult.resultCode, 200);
                assertTrue(offersResult.isSuccessful);
                assertFalse(offersResult.data.getItems().isEmpty());
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getOffersAsyncBoundNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        client.getItemsForBoundAsync(null, null, new SyteCallback<ItemsResult>() {
            @Override
            public void onResult(SyteResult<ItemsResult> offersResult) {
                assertNotNull(offersResult);
                assertNotNull(offersResult.errorMessage);
                assertNull(offersResult.data);
                assertEquals(offersResult.resultCode, -1);
                assertFalse(offersResult.isSuccessful);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getBoundsAsync() throws InterruptedException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        CountDownLatch latch = new CountDownLatch(1);
        UrlImageSearch requestData = createUrlImageSearchRequestData();
        client.getBoundsAsync(requestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> result) {
                assertNotNull(result);
                assertNotNull(result.data);
                assertTrue(result.isSuccessful);
                assertEquals(result.resultCode, 200);

                assertEquals(result.data.getBounds().size(), 4);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getBoundsAsyncImageUrlNull() throws InterruptedException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        CountDownLatch latch = new CountDownLatch(1);
        UrlImageSearch requestData = new UrlImageSearch(null, SyteProductType.CAMERA);
        client.getBoundsAsync(requestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> result) {
                assertNotNull(result);
                assertNull(result.data);
                assertNotNull(result.errorMessage);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getBoundsAsyncProductTypeNull() throws InterruptedException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        CountDownLatch latch = new CountDownLatch(1);
        UrlImageSearch requestData = new UrlImageSearch("test", null);
        client.getBoundsAsync(requestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> result) {
                assertNotNull(result);
                assertNull(result.data);
                assertNotNull(result.errorMessage);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getBoundsWildAsync() throws InterruptedException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        CountDownLatch latch = new CountDownLatch(1);
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(com.syte.ai.android_sdk.test.R.raw.test))
                .appendPath(resources.getResourceTypeName(com.syte.ai.android_sdk.test.R.raw.test))
                .appendPath(resources.getResourceEntryName(com.syte.ai.android_sdk.test.R.raw.test))
                .build();
        ImageSearch requestData = new ImageSearch(uri);
        client.getBoundsAsync(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData,
                syteResult -> {
                    assertNotNull(syteResult);
                    assertNotNull(syteResult.data);
                    assertTrue(syteResult.isSuccessful);
                    assertEquals(syteResult.resultCode, 200);

                    assertEquals(syteResult.data.getBounds().size(), 4);
                    latch.countDown();
                }
        );
        latch.await();
    }

    @Test
    public void getBoundsWildAsyncImageUriNull() throws InterruptedException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        CountDownLatch latch = new CountDownLatch(1);
        ImageSearch requestData = new ImageSearch(null);
        client.getBoundsAsync(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData,
                result -> {
                    assertNotNull(result);
                    assertNull(result.data);
                    assertNotNull(result.errorMessage);
                    assertFalse(result.isSuccessful);
                    assertEquals(result.resultCode, -1);
                    latch.countDown();
                }
        );
        latch.await();
    }

    @Test
    public void getBoundsWild() {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(com.syte.ai.android_sdk.test.R.raw.test))
                .appendPath(resources.getResourceTypeName(com.syte.ai.android_sdk.test.R.raw.test))
                .appendPath(resources.getResourceEntryName(com.syte.ai.android_sdk.test.R.raw.test))
                .build();
        ImageSearch requestData = new ImageSearch(uri);
        SyteResult<BoundsResult> syteResult = client.getBounds(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData
        );
        assertNotNull(syteResult);
        assertNotNull(syteResult.data);
        assertTrue(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, 200);

        assertEquals(syteResult.data.getBounds().size(), 4);
    }

    @Test
    public void getBoundsWildImageUriNull() {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        ImageSearch requestData = new ImageSearch(null);
        SyteResult<BoundsResult> result = client.getBounds(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData
        );
        assertNotNull(result);
        assertNull(result.data);
        assertNotNull(result.errorMessage);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getBoundsWildPng() {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(com.syte.ai.android_sdk.test.R.raw.test3))
                .appendPath(resources.getResourceTypeName(com.syte.ai.android_sdk.test.R.raw.test3))
                .appendPath(resources.getResourceEntryName(com.syte.ai.android_sdk.test.R.raw.test3))
                .build();
        ImageSearch requestData = new ImageSearch(uri);
        SyteResult<BoundsResult> syteResult = client.getBounds(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData
        );
        assertNotNull(syteResult);
        assertNotNull(syteResult.data);
        assertTrue(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, 200);

        assertEquals(syteResult.data.getBounds().size(), 2);
    }

    @Test
    public void getBoundsWildWebp() {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(com.syte.ai.android_sdk.test.R.raw.test4))
                .appendPath(resources.getResourceTypeName(com.syte.ai.android_sdk.test.R.raw.test4))
                .appendPath(resources.getResourceEntryName(com.syte.ai.android_sdk.test.R.raw.test4))
                .build();
        ImageSearch requestData = new ImageSearch(uri);
        SyteResult<BoundsResult> syteResult = client.getBounds(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData
        );
        assertNotNull(syteResult);
        assertNotNull(syteResult.data);
        assertTrue(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, 200);

        assertEquals(syteResult.data.getBounds().size(), 1);
    }

    @Test
    public void getBoundsWildRotationTagPresent() throws SyteGeneralException, IOException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(com.syte.ai.android_sdk.test.R.raw.test_rotation))
                .appendPath(resources.getResourceTypeName(com.syte.ai.android_sdk.test.R.raw.test_rotation))
                .appendPath(resources.getResourceEntryName(com.syte.ai.android_sdk.test.R.raw.test_rotation))
                .build();
        ImageSearch requestData = new ImageSearch(uri);
        SyteResult<BoundsResult> syteResult = client.getBounds(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                requestData
        );
        assertNotNull(syteResult);
        assertNotNull(syteResult.data);
        assertTrue(syteResult.isSuccessful);
        assertEquals(syteResult.resultCode, 200);

        assertEquals(syteResult.data.getBounds().size(), 4);
    }

    private SyteResult<BoundsResult> getBoundsInternal(@Nullable UrlImageSearch urlImageSearch) {
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        UrlImageSearch requestData = urlImageSearch == null ? createUrlImageSearchRequestData() : urlImageSearch;
        return client.getBounds(requestData);
    }

}