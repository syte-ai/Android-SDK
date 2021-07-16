package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.enums.SyteProductType;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

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
        ImageSearchClient client = mInitSyte.retrieveImageSearchClient();
        SyteResult<OffersResult> offersResult = client.getOffers(boundsResult.data.getBounds().get(1), null);
        assertNotNull(offersResult);
        assertNotNull(offersResult.data);
        assertEquals(offersResult.resultCode, 200);
        assertTrue(offersResult.isSuccessful);
        assertFalse(offersResult.data.getOffers().isEmpty());
    }

    @Test
    public void getOffersWithCrop() {
        startSessionInternal();
        SyteResult<BoundsResult> boundsResult = getBoundsInternal(null);
        ImageSearchClient client = mInitSyte.retrieveImageSearchClient();

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
        ImageSearchClient client = mInitSyte.retrieveImageSearchClient();
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
    public void getBoundsAsync() throws InterruptedException {
        startSessionInternal();
        ImageSearchClient client = mInitSyte.retrieveImageSearchClient();
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

    private SyteResult<BoundsResult> getBoundsInternal(@Nullable UrlImageSearchRequestData urlImageSearchRequestData) {
        ImageSearchClient client = mInitSyte.retrieveImageSearchClient();
        UrlImageSearchRequestData requestData = urlImageSearchRequestData == null ? createUrlImageSearchRequestData() : urlImageSearchRequestData;
        return client.getBounds(requestData);
    }

}