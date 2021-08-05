package com.syte.ai.android_sdk.core;

import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import androidx.exifinterface.media.ExifInterface;
import androidx.test.platform.app.InstrumentationRegistry;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.enums.SyteProductType;
import com.syte.ai.android_sdk.exceptions.SyteGeneralException;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class ImageSearchClientImplTest extends BaseTest {

    private UrlImageSearchRequestData createUrlImageSearchRequestData() {
        UrlImageSearchRequestData requestData = new UrlImageSearchRequestData(
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
        assertNotNull(result.data.getFirstBoundOffersResult());
    }

    @Test
    public void getBoundsImageUrlNull() {
        startSessionInternal();
        UrlImageSearchRequestData requestData = new UrlImageSearchRequestData(null, SyteProductType.CAMERA);

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
        UrlImageSearchRequestData requestData = new UrlImageSearchRequestData("test", null);

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

        UrlImageSearchRequestData requestData = new UrlImageSearchRequestData(
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

        UrlImageSearchRequestData requestData = createUrlImageSearchRequestData();
        requestData.setRetrieveOffersForTheFirstBound(false);

        SyteResult<BoundsResult> result = getBoundsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);

        assertEquals(result.data.getBounds().size(), 4);
        assertNull(result.data.getFirstBoundOffersResult());
    }

    @Test
    public void getOffers() {
        startSessionInternal();
        SyteResult<BoundsResult> boundsResult = getBoundsInternal(null);
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        SyteResult<OffersResult> offersResult = client.getOffers(boundsResult.data.getBounds().get(1), null);
        assertNotNull(offersResult);
        assertNotNull(offersResult.data);
        assertEquals(offersResult.resultCode, 200);
        assertTrue(offersResult.isSuccessful);
        assertFalse(offersResult.data.getOffers().isEmpty());
    }

    @Test
    public void getOffersBoundNull() {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        SyteResult<OffersResult> offersResult = client.getOffers(null, null);
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

        SyteResult<OffersResult> offersResult = client.getOffers(boundsResult.data.getBounds().get(1), coordinates);
        assertNotNull(offersResult);
        assertNotNull(offersResult.data);
        assertEquals(offersResult.resultCode, 200);
        assertTrue(offersResult.isSuccessful);
        assertFalse(offersResult.data.getOffers().isEmpty());
    }

    @Test
    public void getOffersAsync() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        SyteResult<BoundsResult> boundsResult = getBoundsInternal(null);
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        client.getOffersAsync(boundsResult.data.getBounds().get(1), null, new SyteCallback<OffersResult>() {
            @Override
            public void onResult(SyteResult<OffersResult> offersResult) {
                assertNotNull(offersResult);
                assertNotNull(offersResult.data);
                assertEquals(offersResult.resultCode, 200);
                assertTrue(offersResult.isSuccessful);
                assertFalse(offersResult.data.getOffers().isEmpty());
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
        client.getOffersAsync(null, null, new SyteCallback<OffersResult>() {
            @Override
            public void onResult(SyteResult<OffersResult> offersResult) {
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
        UrlImageSearchRequestData requestData = createUrlImageSearchRequestData();
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
        UrlImageSearchRequestData requestData = new UrlImageSearchRequestData(null, SyteProductType.CAMERA);
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
        UrlImageSearchRequestData requestData = new UrlImageSearchRequestData("test", null);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(uri);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(null);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(uri);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(null);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(uri);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(uri);
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
        ImageSearchRequestData requestData = new ImageSearchRequestData(uri);
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

    private SyteResult<BoundsResult> getBoundsInternal(@Nullable UrlImageSearchRequestData urlImageSearchRequestData) {
        ImageSearchClient client = mInitSyte.getImageSearchClient();
        UrlImageSearchRequestData requestData = urlImageSearchRequestData == null ? createUrlImageSearchRequestData() : urlImageSearchRequestData;
        return client.getBounds(requestData);
    }

}