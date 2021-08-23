package com.syte.ai.android_sdk.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarProducts;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.ItemsResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.SyteProductType;
import com.syte.ai.android_sdk.exceptions.SyteGeneralException;
import com.syte.ai.android_sdk.util.SyteLogger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

class SyteRemoteDataSource extends BaseRemoteDataSource {

    private static final String TAG = SyteRemoteDataSource.class.getSimpleName();

    private static final String SYTE_URL = "https://cdn.syteapi.com";
    private static final String EXIF_REMOVAL_URL = "https://imagemod.syteapi.com";

    private interface BoundsResultCallback {
        void onResult(SyteResult<BoundsResult> result);
    }

    private interface ExifRemovalResultCallback {
        void onResult(UrlImageSearch requestData, Throwable e);
    }

    private final SyteService mSyteService;
    private final ExifRemovalService mExifRemovalService;
    private final RecommendationRemoteDataSource mRecommendationRemoteDataSource;
    private final TextSearchRemoteDataSource mTextSearchRemoteDataSource;

    SyteRemoteDataSource(SyteConfiguration configuration) {
        Retrofit retrofit = RetrofitBuilder.build(SYTE_URL);
        Retrofit exifRemovalRetrofit = RetrofitBuilder.build(EXIF_REMOVAL_URL);

        mSyteService = retrofit.create(SyteService.class);
        mExifRemovalService = exifRemovalRetrofit.create(ExifRemovalService.class);
        mRecommendationRemoteDataSource = new RecommendationRemoteDataSource(mSyteService, configuration);
        mTextSearchRemoteDataSource = new TextSearchRemoteDataSource(mSyteService, configuration);

        mConfiguration = configuration;
    }

    public void getAutoCompleteAsync(String query, String lang, SyteCallback<AutoCompleteResult> callback) {
        renewTimestamp();
        mTextSearchRemoteDataSource.getAutoCompleteAsync(query, lang, callback);
    }

    SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch) {
        renewTimestamp();
        return mTextSearchRemoteDataSource.getTextSearch(textSearch);
    }

    public void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback) {
        renewTimestamp();
        mTextSearchRemoteDataSource.getTextSearchAsync(textSearch, callback);
    }

    SyteResult<List<String>> getPopularSearch(String lang) {
        renewTimestamp();
        return mTextSearchRemoteDataSource.getPopularSearch(lang);
    }

    void getPopularSearchAsync(
            String lang,
            SyteCallback<List<String>> callback
    ) {
        renewTimestamp();
        mTextSearchRemoteDataSource.getPopularSearchAsync(lang, callback);
    }

    SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProducts similarProducts
    ) {
        renewTimestamp();
        return mRecommendationRemoteDataSource.getSimilarProducts(similarProducts);
    }

    void getSimilarProductsAsync(
            SimilarProducts similarProducts,
            SyteCallback<SimilarProductsResult> callback
    ) {
        renewTimestamp();
        mRecommendationRemoteDataSource.getSimilarProductsAsync(
                similarProducts,
                callback
        );
    }

    SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLook shopTheLook,
            SytePlatformSettings sytePlatformSettings
    ) {
        renewTimestamp();
        return mRecommendationRemoteDataSource.getShopTheLook(shopTheLook, sytePlatformSettings);
    }

    void getShopTheLookAsync(
            ShopTheLook shopTheLook,
            SytePlatformSettings sytePlatformSettings,
            SyteCallback<ShopTheLookResult> callback
    ) {
        renewTimestamp();
        mRecommendationRemoteDataSource.getShopTheLookAsync(
                shopTheLook,
                sytePlatformSettings,
                callback
        );
    }

    SyteResult<PersonalizationResult> getPersonalization(
            Personalization personalization
    ) {
        renewTimestamp();
        return mRecommendationRemoteDataSource.getPersonalization(personalization);
    }

    void getPersonalizationAsync(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback
    ) {
        renewTimestamp();
        mRecommendationRemoteDataSource.getPersonalizationAsync(
                personalization,
                callback
        );
    }

    SyteResult<SytePlatformSettings> initialize() {
        renewTimestamp();
        Response<ResponseBody> response = null;
        SyteResult<SytePlatformSettings> syteResult = new SyteResult<>();
        try {
            response = mSyteService.initialize(mConfiguration.getAccountId()).execute();
        } catch (IOException e) {
            return handleException(response, e);
        }
        try {
            if (response.body() == null) {
                return handleEmptyBody(response);
            }
            syteResult.data =
                    new Gson().fromJson(response.body().string(), SytePlatformSettings.class);
        } catch (IOException e) {
            return handleException(response, e);
        }
        syteResult.resultCode = response.code();
        syteResult.isSuccessful = response.isSuccessful();
        return syteResult;
    }

    void initializeAsync(SyteCallback<SytePlatformSettings> callback) {
        renewTimestamp();
        mSyteService.initialize(mConfiguration.getAccountId()).enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        SyteResult<SytePlatformSettings> syteResult = new SyteResult<>();
                        try {
                            if (response.body() == null) {
                                callback.onResult(handleEmptyBody(response));
                            }
                            syteResult.data =
                                    new Gson().fromJson(response.body().string(), SytePlatformSettings.class);
                        } catch (IOException e) {
                            callback.onResult(handleException(response, e));
                        }
                        syteResult.resultCode = response.code();
                        syteResult.isSuccessful = response.isSuccessful();
                        callback.onResult(syteResult);
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        callback.onResult(handleOnFailure(t));
                    }
                }
        );
    }

    private Call<ResponseBody> generateBoundsCall(
            UrlImageSearch requestData,
            SytePlatformSettings sytePlatformSettings
    ) {
        String catalog;
        try {
            catalog = sytePlatformSettings
                    .getData()
                    .getProducts()
                    .getSyteapp()
                    .getFeatures()
                    .getBoundingBox()
                    .getCropper()
                    .getCatalog();
        } catch (Exception e) {
            catalog = null;
        }
        return mSyteService.getBounds(
                mConfiguration.getAccountId(),
                mConfiguration.getApiSignature(),
                requestData.getPersonalizedRanking() ? mConfiguration.getUserId() : null,
                requestData.getPersonalizedRanking() ? Long.toString(mConfiguration.getSessionId()) : null,
                requestData.getProductType().name,
                mConfiguration.getLocale(),
                catalog,
                requestData.getSku(),
                requestData.getImageUrl(),
                requestData.getPersonalizedRanking() ?
                        Utils.viewedProductsString(mConfiguration.getViewedProducts()) : null
        );
    }

    SyteResult<BoundsResult> getBounds(UrlImageSearch requestData, SytePlatformSettings sytePlatformSettings) {
        renewTimestamp();
        Response<ResponseBody> response = null;
        try {
            response = generateBoundsCall(requestData, sytePlatformSettings).execute();
            return onBoundsResult(requestData,
                    response,
                    requestData.getFirstBoundItemsCoordinates(),
                    sytePlatformSettings,
                    true,
                    null
            );
        } catch (IOException e) {
            return handleException(response, e);
        }
    }

    void getBoundsAsync(UrlImageSearch requestData,
                        SytePlatformSettings sytePlatformSettings,
                        SyteCallback<BoundsResult> callback) {
        renewTimestamp();
        generateBoundsCall(requestData, sytePlatformSettings).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {
                try {
                    onBoundsResult(
                            requestData,
                            response,
                            requestData.getFirstBoundItemsCoordinates(),
                            sytePlatformSettings,
                            false,
                            new BoundsResultCallback() {
                                @Override
                                public void onResult(SyteResult<BoundsResult> result) {
                                    callback.onResult(result);
                                }
                            });
                } catch (IOException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }

        });
    }

    SyteResult<ItemsResult> getOffers(
            Bound bound,
            @Nullable CropCoordinates cropCoordinates,
            SytePlatformSettings sytePlatformSettings
    ) {
        renewTimestamp();
        SyteResult<ItemsResult> syteResult = new SyteResult<>();
        Response<ResponseBody> response = null;
        try {
            response = generateOffersCall(
                    bound.getItemUrl(),
                    cropCoordinates,
                    sytePlatformSettings
            ).execute();
            syteResult.isSuccessful = response.isSuccessful();
            syteResult.resultCode = response.code();
            syteResult.data = onOffersResult(response);
            if (response.errorBody() != null) {
                syteResult.errorMessage = response.errorBody().string();
            }
            return syteResult;
        } catch (IOException e) {
            return handleException(response, e);
        }
    }

    void getOffersAsync(
            Bound bound,
            CropCoordinates cropCoordinates,
            SytePlatformSettings sytePlatformSettings,
            SyteCallback<ItemsResult> callback) {
        renewTimestamp();
        generateOffersCall(
                bound.getItemUrl(),
                cropCoordinates,
                sytePlatformSettings
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                SyteResult<ItemsResult> syteResult = new SyteResult<>();
                syteResult.isSuccessful = response.isSuccessful();
                syteResult.resultCode = response.code();
                try {
                    syteResult.data = onOffersResult(response);
                    if (response.errorBody() != null) {
                        syteResult.errorMessage = response.errorBody().string();
                    }
                    callback.onResult(syteResult);
                } catch (IOException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }
        });
    }

    private SyteResult<BoundsResult> onBoundsResult(
            UrlImageSearch requestData,
            Response<ResponseBody> response,
            @Nullable CropCoordinates cropCoordinates,
            SytePlatformSettings sytePlatformSettings,
            boolean sync,
            @Nullable BoundsResultCallback resultCallback
    ) throws IOException {
        renewTimestamp();
        SyteResult<BoundsResult> syteResult = new SyteResult<>();
        syteResult.isSuccessful = response.isSuccessful();
        syteResult.resultCode = response.code();

        if (response.body() == null) {
            if (resultCallback != null) {
                resultCallback.onResult(handleEmptyBody(response));
            }
            return handleEmptyBody(response);
        }

        String modifiedResponseString = null;
        try {
            modifiedResponseString =
                    response.body().string().replace(requestData.getImageUrl(), "bounds");
        } catch (IOException e) {
            if (resultCallback != null) {
                resultCallback.onResult(handleException(response, e));
            }
            return handleException(response, e);
        }

        try {
            syteResult.data = new Gson().fromJson(modifiedResponseString, BoundsResult.class);
        } catch (Exception e) {
            if (resultCallback != null) {
                resultCallback.onResult(handleException(response, e));
            }
            return handleException(response, e);
        }

        if (syteResult.data == null) {
            if (resultCallback != null) {
                resultCallback.onResult(syteResult);
            }
            return syteResult;
        }

        if (syteResult.data.getBounds() == null || syteResult.data.getBounds().size() == 0) {
            if (resultCallback != null) {
                resultCallback.onResult(syteResult);
            }
            return syteResult;
        }

        if (requestData.isRetrieveItemsForTheFirstBound()) {
            if (sync) {
                Response<ResponseBody> offersResponse = null;
                try {
                    offersResponse = generateOffersCall(
                            syteResult.data.getBounds().get(0).getItemUrl(),
                            cropCoordinates,
                            sytePlatformSettings
                    ).execute();
                } catch (IOException e) {
                    return handleException(response, e);
                }
                syteResult.data.setFirstBoundItemsResult(onOffersResult(offersResponse));
                return syteResult;
            } else {
                generateOffersCall(
                        syteResult.data.getBounds().get(0).getItemUrl(),
                        cropCoordinates,
                        sytePlatformSettings
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        try {
                            syteResult.data.setFirstBoundItemsResult(onOffersResult(response));
                            if (resultCallback != null) {
                                resultCallback.onResult(syteResult);
                            }
                        } catch (IOException e) {
                            if (resultCallback != null) {
                                resultCallback.onResult(handleException(response, e));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        if (resultCallback != null) {
                            resultCallback.onResult(handleOnFailure(t));
                        }
                    }
                });
            }
        } else if (resultCallback != null) {
            resultCallback.onResult(syteResult);
        }
        return syteResult;
    }

    private Call<ResponseBody> generateOffersCall(
            String offersUrl,
            CropCoordinates cropCoordinates,
            SytePlatformSettings sytePlatformSettings
    ) {
        boolean cropEnabled = cropCoordinates != null;
        String actualUrl = null;
        String coordinatesBase64 = null;

        if (cropEnabled) {
            byte[] coordinateStringBytes = cropCoordinates.toString().getBytes();
            Uri uri = Uri.parse(offersUrl);
            final Set<String> params = uri.getQueryParameterNames();
            final Uri.Builder newUri = uri.buildUpon().clearQuery();

            coordinatesBase64 = Base64.encodeToString(coordinateStringBytes, Base64.DEFAULT);

            SyteLogger.i(TAG, "Encoded coordinates: " + coordinatesBase64);

            for (String param : params) {
                if (param.equals("cats")
                        || param.equals("crop")
                        || param.equals("catalog")
                        || param.equals("feed")
                ) continue;
                newUri.appendQueryParameter(param, uri.getQueryParameter(param));
            }

            actualUrl = newUri.toString();
        } else {
            actualUrl = offersUrl;
        }

        return mSyteService.getOffers(
                actualUrl,
                coordinatesBase64,
                cropEnabled ? Catalog.GENERAL.getName() : null,
                cropEnabled ? sytePlatformSettings
                        .getData()
                        .getProducts()
                        .getSyteapp()
                        .getFeatures()
                        .getBoundingBox()
                        .getCropper()
                        .getCatalog() : null

        );
    }

    private ItemsResult onOffersResult(Response<ResponseBody> offersResponse) throws IOException {
        if (offersResponse.isSuccessful()
                && offersResponse.body() != null) {

            String offersResponseString = offersResponse.body().string();
            ItemsResult itemsResult = new Gson().fromJson(
                    offersResponseString,
                    ItemsResult.class
            );

            try {
                JSONObject jsonObject = new JSONObject(offersResponseString);
                JSONArray offersArray = jsonObject.getJSONArray("ads");
                for (int i = 0; i < itemsResult.getItems().size(); i++) {
                    itemsResult.getItems().get(i).setOriginalData(offersArray.getJSONObject(i)
                            .getJSONObject("original_data"));
                }
            } catch (Exception e) {
                handleException(offersResponse, e);
            }

            return itemsResult;
        } else return null;
    }

    public SyteResult<BoundsResult> getBoundsWild(
            Context context,
            ImageSearch requestData,
            SytePlatformSettings sytePlatformSettings
    ) {

        ImageSearchClientImpl imageSearchClient = new ImageSearchClientImpl(
                this,
                sytePlatformSettings
        );
        UrlImageSearch urlImageSearch;
        try {
            urlImageSearch = prepareImageSearchRequestData(
                    context,
                    requestData,
                    sytePlatformSettings
            );
        } catch (Exception e) {
            return handleException(null, e);
        }
        return imageSearchClient.getBounds(urlImageSearch);
    }

    void getBoundsWildAsync(
            Context context,
            ImageSearch requestData,
            SytePlatformSettings sytePlatformSettings,
            SyteCallback<BoundsResult> callback
    ) {
        ImageSearchClientImpl imageSearchClient = new ImageSearchClientImpl(
                this,
                sytePlatformSettings
        );
        prepareImageSearchRequestDataAsync(
                context,
                requestData,
                sytePlatformSettings,
                new ExifRemovalResultCallback() {
                    @Override
                    public void onResult(UrlImageSearch requestData, Throwable e) {
                        if (e != null) {
                            callback.onResult(handleException(null, e));
                        } else {
                            imageSearchClient.getBoundsAsync(requestData, callback);
                        }
                    }
                }
        );
    }

    private UrlImageSearch prepareImageSearchRequestData(
            Context context,
            ImageSearch requestData,
            SytePlatformSettings sytePlatformSettings
    ) throws IOException, JSONException, SyteGeneralException {

        byte[] bytes = prepareImage(context, requestData, sytePlatformSettings);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), bytes);

        Response<ResponseBody> response = mExifRemovalService.removeTags(
                mConfiguration.getAccountId(),
                mConfiguration.getApiSignature(),
                requestBody
        ).execute();
        ResponseBody body = response.body();

        if (body != null) {
            JSONObject jsonObject = new JSONObject(body.string());
            UrlImageSearch urlImageSearch = new UrlImageSearch(
                    jsonObject.getString("url"),
                    SyteProductType.DISCOVERY_BUTTON
            );
            urlImageSearch.setRetrieveItemsForTheFirstBound(
                    requestData.isRetrieveItemsForTheFirstBound()
            );
            urlImageSearch.setFirstBoundItemsCoordinates(
                    requestData.getFirstBoundItemsCoordinates()
            );
            urlImageSearch.setPersonalizedRanking(
                    requestData.getPersonalizedRanking()
            );
            return urlImageSearch;
        } else {
            throw new SyteGeneralException("Exif removal service returned empty body.");
        }
    }

    private void prepareImageSearchRequestDataAsync(
            Context context,
            ImageSearch requestData,
            SytePlatformSettings sytePlatformSettings,
            ExifRemovalResultCallback callback
    ) {
        byte[] bytes = new byte[0];
        try {
            bytes = prepareImage(context, requestData, sytePlatformSettings);
        } catch (Exception e) {
            callback.onResult(null, e);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), bytes);

        mExifRemovalService.removeTags(
                mConfiguration.getAccountId(),
                mConfiguration.getApiSignature(),
                requestBody
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        UrlImageSearch urlImageSearch = new UrlImageSearch(
                                jsonObject.getString("url"),
                                SyteProductType.DISCOVERY_BUTTON
                        );
                        urlImageSearch.setRetrieveItemsForTheFirstBound(
                                requestData.isRetrieveItemsForTheFirstBound()
                        );
                        urlImageSearch.setFirstBoundItemsCoordinates(
                                requestData.getFirstBoundItemsCoordinates()
                        );
                        urlImageSearch.setPersonalizedRanking(
                                requestData.getPersonalizedRanking()
                        );
                        callback.onResult(urlImageSearch, null);
                    } catch (IOException | JSONException e) {
                        callback.onResult(null, e);
                    }
                } else if (response.errorBody() != null) {
                    try {
                        callback.onResult(null, new SyteGeneralException(response.errorBody().string()));
                    } catch (IOException e) {
                        callback.onResult(null, e);
                    }
                } else {
                    callback.onResult(null, new SyteGeneralException("Error while making request to the EXIF tag removal service."));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                callback.onResult(null, t);
            }
        });
    }

    @Override
    public void setConfiguration(SyteConfiguration configuration) {
        super.setConfiguration(configuration);
        mRecommendationRemoteDataSource.setConfiguration(configuration);
        mTextSearchRemoteDataSource.setConfiguration(configuration);
    }

    private byte[] prepareImage(
            Context context,
            ImageSearch requestData,
            SytePlatformSettings sytePlatformSettings) throws IOException, SyteGeneralException {

        ImageProcessor imageProcessor = new ImageProcessor();
        Bitmap bitmap = imageProcessor.rotateImageIfNeeded(
                context,
                requestData.getImageUri()
        );

        File notCompressedImage = new File(requestData.getImageUri().toString());
        long size = 0;
        if (notCompressedImage.exists()) {
            size = notCompressedImage.length();
        }

        File file = imageProcessor.compress(
                context,
                size,
                bitmap,
                Utils.getImageScale(sytePlatformSettings)
        );

        SyteLogger.i(TAG, "Compressed image size: " + file.length() + " bytes");

        return Utils.getFileBytes(file);
    }

}
