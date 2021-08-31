package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarItems;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProductRecommendationClientImplTest extends BaseTest {

    @Test
    public void getSimilarProducts() {
        startSessionInternal();
        SyteResult<SimilarProductsResult> result = getSimilarsInternal(null);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().size(), 7);
        assertNotNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getSimilarProductsRequestDataNull() {
        startSessionInternal();
        SyteResult<SimilarProductsResult> result = mInitSyte.getProductRecommendationClient().getSimilarProducts(null);
        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getSimilarProductsImageUrlNull() {
        startSessionInternal();
        SimilarItems requestData = new SimilarItems("test", null);
        SyteResult<SimilarProductsResult> result =
                mInitSyte.getProductRecommendationClient()
                        .getSimilarProducts(requestData);
        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getSimilarProductsSkuNull() {
        startSessionInternal();
        SimilarItems requestData = new SimilarItems(null, "test");
        SyteResult<SimilarProductsResult> result =
                mInitSyte.getProductRecommendationClient()
                        .getSimilarProducts(requestData);
        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getSimilarProductsWithNonDefaultLimit() {
        startSessionInternal();
        SimilarItems requestData = createSimilarProductsRequestData();
        requestData.setLimit(20);

        SyteResult<SimilarProductsResult> result = getSimilarsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().size(), 20);
        assertNotNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getSimilarProductsWithNonDefaultSyteUrlReferer() {
        startSessionInternal();
        SimilarItems requestData = createSimilarProductsRequestData();
        requestData.setLimit(20);
        requestData.setSyteUrlReferer("test");

        SyteResult<SimilarProductsResult> result = getSimilarsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().size(), 20);
        assertNotNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getSimilarProductsWithNonDefaultReturnFieldsSku() {
        startSessionInternal();
        SimilarItems requestData = createSimilarProductsRequestData();
        requestData.setLimit(20);
        requestData.setSyteUrlReferer("test");
        requestData.setFieldsToReturn(RecommendationReturnField.SKU);

        SyteResult<SimilarProductsResult> result = getSimilarsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().size(), 20);
        assertNull(result.data.getItems().get(0).getImageUrl());
        assertNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getSimilarProductsWithNonDefaultReturnFieldsImageUrl() {
        startSessionInternal();
        SimilarItems requestData = createSimilarProductsRequestData();
        requestData.setLimit(20);
        requestData.setSyteUrlReferer("test");
        requestData.setFieldsToReturn(RecommendationReturnField.IMAGE_URL);

        SyteResult<SimilarProductsResult> result = getSimilarsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().size(), 20);
        assertNull(result.data.getItems().get(0).getSku());
        assertNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getImageUrl());
    }

    @Test
    public void getSimilarProductsWithNonDefaultReturnFieldsImageUrlAndSku() {
        startSessionInternal();
        SimilarItems requestData = createSimilarProductsRequestData();
        requestData.setLimit(20);
        requestData.setSyteUrlReferer("test");
        requestData.setFieldsToReturn(RecommendationReturnField.IMAGE_URL_AND_SKU);

        SyteResult<SimilarProductsResult> result = getSimilarsInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().size(), 20);
        assertNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getImageUrl());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getSimilarProductsAsync() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getSimilarProductsAsync(createSimilarProductsRequestData(), new SyteCallback<SimilarProductsResult>() {
            @Override
            public void onResult(SyteResult<SimilarProductsResult> result) {
                assertNotNull(result);
                assertNotNull(result.data);
                assertTrue(result.isSuccessful);
                assertEquals(result.resultCode, 200);
                assertEquals(result.data.getItems().size(), 7);
                assertNotNull(result.data.getItems().get(0).getOriginalData());
                assertNotNull(result.data.getItems().get(0).getSku());
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getSimilarProductsAsyncRequestDataNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getSimilarProductsAsync(null, new SyteCallback<SimilarProductsResult>() {
            @Override
            public void onResult(SyteResult<SimilarProductsResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getSimilarProductsAsyncImageUrlNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        SimilarItems requestData = new SimilarItems("test", null);
        client.getSimilarProductsAsync(requestData, new SyteCallback<SimilarProductsResult>() {
            @Override
            public void onResult(SyteResult<SimilarProductsResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getSimilarProductsAsyncSkuNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        SimilarItems requestData = new SimilarItems(null, "test");
        client.getSimilarProductsAsync(requestData, new SyteCallback<SimilarProductsResult>() {
            @Override
            public void onResult(SyteResult<SimilarProductsResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getShopTheLook() {
        startSessionInternal();
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(null);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 3);
    }

    @Test
    public void getShopTheLookRequestDataNull() {
        startSessionInternal();
        SyteResult<ShopTheLookResult> result =
                mInitSyte.getProductRecommendationClient().getShopTheLook(null);

        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getShopTheLookImageUrlNull() {
        startSessionInternal();
        ShopTheLook requestData = new ShopTheLook("test", null);
        SyteResult<ShopTheLookResult> result =
                mInitSyte.getProductRecommendationClient().getShopTheLook(requestData);

        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getShopTheLookSkuNull() {
        startSessionInternal();
        ShopTheLook requestData = new ShopTheLook(null, "test");
        SyteResult<ShopTheLookResult> result =
                mInitSyte.getProductRecommendationClient().getShopTheLook(requestData);

        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getShopTheLookWithNonDefaultLimit() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setLimit(20);
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
    }

    @Test
    public void getShopTheLookWithNonDefaultUrlReferer() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setSyteUrlReferer("test");
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 3);
    }

    @Test
    public void getShopTheLookWithNonDefaultOriginalItem() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setSyteOriginalItem("test");
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 3);
    }

    @Test
    public void getShopTheLookWithNonDefaultReturnFieldsSku() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setFieldsToReturn(RecommendationReturnField.SKU);
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 3);
        assertNull(result.data.getItems().get(0).getItems().get(0).getImageUrl());
        assertNull(result.data.getItems().get(0).getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getItems().get(0).getSku());
    }

    @Test
    public void getShopTheLookWithNonDefaultReturnFieldsImageUrl() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setFieldsToReturn(RecommendationReturnField.IMAGE_URL);
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 3);
        assertNull(result.data.getItems().get(0).getItems().get(0).getSku());
        assertNull(result.data.getItems().get(0).getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getItems().get(0).getImageUrl());
    }

    @Test
    public void getShopTheLookWithNonDefaultReturnFieldsImageUrlAndSku() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setFieldsToReturn(RecommendationReturnField.IMAGE_URL_AND_SKU);
        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 3);
        assertNull(result.data.getItems().get(0).getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getItems().get(0).getImageUrl());
        assertNotNull(result.data.getItems().get(0).getItems().get(0).getSku());
    }

    @Test
    public void getShopTheLookNonDefaultLimitPerBound() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setLimitPerBound(4);

        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 4);
    }

    @Test
    public void getShopTheLookAllOffers() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setLimitPerBound(4);

        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 4);
        assertEquals(result.data.getItemsForAllLabels(false).get(0).getBbCategories().get(0), "Trousers");
        assertEquals(result.data.getItemsForAllLabels(false).get(1).getBbCategories().get(0), "Trousers");
    }

    @Test
    public void getShopTheLookAllOffersZipEnabled() {
        startSessionInternal();
        ShopTheLook requestData = createShopTheLookRequestData();
        requestData.setLimitPerBound(4);

        SyteResult<ShopTheLookResult> result = getShopTheLookInternal(requestData);

        assertNotNull(result);
        assertNotNull(result.data);
        assertNotNull(result.data.getItems());
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertEquals(result.data.getItems().get(0).getItems().size(), 4);
        assertEquals(result.data.getItemsForAllLabels(true).get(0).getBbCategories().get(0), "Trousers");
        assertEquals(result.data.getItemsForAllLabels(true).get(1).getBbCategories().get(0), "Necklaces");
    }

    @Test
    public void getShopTheLookAsync() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getShopTheLookAsync(createShopTheLookRequestData(), new SyteCallback<ShopTheLookResult>() {
            @Override
            public void onResult(SyteResult<ShopTheLookResult> result) {
                assertNotNull(result);
                assertNotNull(result.data);
                assertNotNull(result.data.getItems());
                assertTrue(result.isSuccessful);
                assertEquals(result.resultCode, 200);
                assertEquals(result.data.getItems().get(0).getItems().size(), 3);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getShopTheLookAsyncRequestDataNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getShopTheLookAsync(null, new SyteCallback<ShopTheLookResult>() {
            @Override
            public void onResult(SyteResult<ShopTheLookResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getShopTheLookAsyncImageUrlNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ShopTheLook requestData = new ShopTheLook("test", null);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getShopTheLookAsync(requestData, new SyteCallback<ShopTheLookResult>() {
            @Override
            public void onResult(SyteResult<ShopTheLookResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getShopTheLookAsyncSkuNull() throws InterruptedException {
        startSessionInternal();
        CountDownLatch latch = new CountDownLatch(1);
        ShopTheLook requestData = new ShopTheLook(null, "test");
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getShopTheLookAsync(requestData, new SyteCallback<ShopTheLookResult>() {
            @Override
            public void onResult(SyteResult<ShopTheLookResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getPersonalization() throws SyteWrongInputException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        SyteResult<PersonalizationResult> result = getPersonalizationInternal(null);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertNotNull(result.data.getItems());
    }

    @Test
    public void getPersonalizationNoViewedProducts() throws SyteWrongInputException {
        startSessionInternal();
        SyteResult<PersonalizationResult> result = getPersonalizationInternal(null);
        assertNotNull(result);
        assertNotNull(result.errorMessage);
        assertNull(result.data);
        assertFalse(result.isSuccessful);
        assertEquals(result.resultCode, -1);
    }

    @Test
    public void getPersonalizationWithNonDefaultLimit() throws SyteWrongInputException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        Personalization requestData = new Personalization();
        requestData.setLimit(20);

        SyteResult<PersonalizationResult> result = getPersonalizationInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertNotNull(result.data.getItems());
    }

    @Test
    public void getPersonalizationWithNonDefaultSyteUrlReferer() throws SyteWrongInputException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        Personalization requestData = new Personalization();
        requestData.setSyteUrlReferer("test");

        SyteResult<PersonalizationResult> result = getPersonalizationInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertNotNull(result.data.getItems());
    }

    @Test
    public void getPersonalizationWithNonDefaultReturnFieldsSku() throws SyteWrongInputException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        Personalization requestData = new Personalization();
        requestData.setFieldsToReturn(RecommendationReturnField.SKU);

        SyteResult<PersonalizationResult> result = getPersonalizationInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertNotNull(result.data.getItems());
        assertNull(result.data.getItems().get(0).getImageUrl());
        assertNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getPersonalizationWithNonDefaultReturnFieldsImageUrl() throws SyteWrongInputException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        Personalization requestData = new Personalization();
        requestData.setFieldsToReturn(RecommendationReturnField.IMAGE_URL);

        SyteResult<PersonalizationResult> result = getPersonalizationInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertNotNull(result.data.getItems());
        assertNull(result.data.getItems().get(0).getSku());
        assertNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getImageUrl());
    }

    @Test
    public void getPersonalizationWithNonDefaultReturnFieldsImageUrlAndSku() throws SyteWrongInputException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        Personalization requestData = new Personalization();
        requestData.setFieldsToReturn(RecommendationReturnField.IMAGE_URL_AND_SKU);

        SyteResult<PersonalizationResult> result = getPersonalizationInternal(requestData);
        assertNotNull(result);
        assertNotNull(result.data);
        assertTrue(result.isSuccessful);
        assertEquals(result.resultCode, 200);
        assertNotNull(result.data.getItems());
        assertNull(result.data.getItems().get(0).getOriginalData());
        assertNotNull(result.data.getItems().get(0).getImageUrl());
        assertNotNull(result.data.getItems().get(0).getSku());
    }

    @Test
    public void getPersonalizationAsync() throws SyteWrongInputException, InterruptedException {
        startSessionInternal();
        mInitSyte.addViewedItem("PZZ70556-105");
        CountDownLatch latch = new CountDownLatch(1);
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getPersonalizedProductsAsync(createPersonalizationRequestData(), new SyteCallback<PersonalizationResult>() {
            @Override
            public void onResult(SyteResult<PersonalizationResult> result) {
                assertNotNull(result);
                assertNotNull(result.data);
                assertTrue(result.isSuccessful);
                assertEquals(result.resultCode, 200);
                assertNotNull(result.data.getItems());
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    public void getPersonalizationAsyncNoViewedProducts() throws SyteWrongInputException {
        startSessionInternal();

        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        client.getPersonalizedProductsAsync(createPersonalizationRequestData(), new SyteCallback<PersonalizationResult>() {
            @Override
            public void onResult(SyteResult<PersonalizationResult> result) {
                assertNotNull(result);
                assertNotNull(result.errorMessage);
                assertNull(result.data);
                assertFalse(result.isSuccessful);
                assertEquals(result.resultCode, -1);
            }
        });

    }

    private SimilarItems createSimilarProductsRequestData() {
        return new SimilarItems(
                "13705596",
                "https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg"
        );
    }

    private ShopTheLook createShopTheLookRequestData() {
        return new ShopTheLook(
                "PZZ70556-105",
                "https://sytestorageeu.blob.core.windows.net/text-static-feeds/boohoo_direct/PZZ70556-105.jpg?se=2023-10-31T19%3A05%3A46Z&sp=r&sv=2018-03-28&sr=b&sig=DQe1/iuTzLpl/hZhMzmb5jJF8qw41GdNlREzZvunw4k%3D"
        );
    }

    private Personalization createPersonalizationRequestData() {
        return new Personalization();
    }

    private SyteResult<PersonalizationResult> getPersonalizationInternal(@Nullable Personalization requestData) {
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        return client.getPersonalizedProducts(requestData == null ? createPersonalizationRequestData() : requestData);
    }

    private SyteResult<SimilarProductsResult> getSimilarsInternal(@Nullable SimilarItems requestData) {
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        return client.getSimilarProducts(requestData == null ? createSimilarProductsRequestData() : requestData);
    }

    private SyteResult<ShopTheLookResult> getShopTheLookInternal(@Nullable ShopTheLook requestData) {
        ProductRecommendationClient client = mInitSyte.getProductRecommendationClient();
        return client.getShopTheLook(requestData == null ? createShopTheLookRequestData() : requestData);
    }

}