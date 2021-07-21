package com.syte.ai.android_sdk.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.SyteProductType;
import com.syte.ai.android_sdk.exceptions.SyteGeneralException;
import com.syte.ai.android_sdk.util.SyteLogger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
        void onResult(UrlImageSearchRequestData requestData, Throwable e);
    }

    private final SyteService mSyteService;
    private final ExifRemovalService mExifRemovalService;
    private final RecommendationRemoteDataSource mRecommendationRemoteDataSource;

    SyteRemoteDataSource(SyteConfiguration configuration) {
        Retrofit retrofit = RetrofitBuilder.build(SYTE_URL);
        Retrofit exifRemovalRetrofit = RetrofitBuilder.build(EXIF_REMOVAL_URL);

        mSyteService = retrofit.create(SyteService.class);
        mExifRemovalService = exifRemovalRetrofit.create(ExifRemovalService.class);
        mRecommendationRemoteDataSource = new RecommendationRemoteDataSource(mSyteService, configuration);

        mConfiguration = configuration;
    }

    SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProductsRequestData similarProductsRequestData
    ) {
        renewTimestamp();
        return mRecommendationRemoteDataSource.getSimilarProducts(similarProductsRequestData);
    }

    void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback
    ) {
        renewTimestamp();
        mRecommendationRemoteDataSource.getSimilarProductsAsync(
                similarProductsRequestData,
                new SyteCallback<SimilarProductsResult>() {
                    @Override
                    public void onResult(SyteResult<SimilarProductsResult> syteResult) {
                        callback.onResult(syteResult);
                    }
                }
        );
    }

    SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLookRequestData shopTheLookRequestData,
            AccountDataService accountDataService
    ) {
        renewTimestamp();
        return mRecommendationRemoteDataSource.getShopTheLook(shopTheLookRequestData, accountDataService);
    }

    void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            AccountDataService accountDataService,
            SyteCallback<ShopTheLookResult> callback
    ) {
        renewTimestamp();
        mRecommendationRemoteDataSource.getShopTheLookAsync(
                shopTheLookRequestData,
                accountDataService,
                new SyteCallback<ShopTheLookResult>() {
                    @Override
                    public void onResult(SyteResult<ShopTheLookResult> syteResult) {
                        callback.onResult(syteResult);
                    }
                }
        );
    }

    SyteResult<PersonalizationResult> getPersonalization(
            PersonalizationRequestData personalizationRequestData
    ) {
        renewTimestamp();
        return mRecommendationRemoteDataSource.getPersonalization(personalizationRequestData);
    }

    void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback
    ) {
        renewTimestamp();
        mRecommendationRemoteDataSource.getPersonalizationAsync(
                personalizationRequestData,
                new SyteCallback<PersonalizationResult>() {
                    @Override
                    public void onResult(SyteResult<PersonalizationResult> syteResult) {
                        callback.onResult(syteResult);
                    }
                }
        );
    }

    SyteResult<AccountDataService> initialize() {
        renewTimestamp();
        Response<ResponseBody> response = null;
        SyteResult<AccountDataService> syteResult = new SyteResult<>();
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
                    new Gson().fromJson(response.body().string(), AccountDataService.class);
        } catch (IOException e) {
            return handleException(response, e);
        }
        syteResult.resultCode = response.code();
        syteResult.isSuccessful = response.isSuccessful();
        return syteResult;
    }

    void initializeAsync(SyteCallback<AccountDataService> callback) {
        renewTimestamp();
        mSyteService.initialize(mConfiguration.getAccountId()).enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        SyteResult<AccountDataService> syteResult = new SyteResult<>();
                        try {
                            if (response.body() == null) {
                                callback.onResult(handleEmptyBody(response));
                            }
                            syteResult.data =
                                    new Gson().fromJson(response.body().string(), AccountDataService.class);
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
            UrlImageSearchRequestData requestData,
            AccountDataService accountDataService
    ) {
        String catalog;
        try {
            catalog = accountDataService
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
                mConfiguration.getSignature(),
                mConfiguration.getUserId(),
                Long.toString(mConfiguration.getSessionId()),
                requestData.getProductType().name,
                mConfiguration.getLocale(),
                catalog,
                requestData.getSku(),
                requestData.getImageUrl(),
                requestData.getPersonalizedRanking() ?
                        mConfiguration.getSessionSkusString() : null
        );
    }

    SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData requestData, AccountDataService accountDataService) {
        renewTimestamp();
        Response<ResponseBody> response = null;
        try {
            response = generateBoundsCall(requestData, accountDataService).execute();
            return onBoundsResult(requestData,
                    response,
                    requestData.getFirstBoundOffersCoordinates(),
                    accountDataService,
                    true,
                    null
            );
        } catch (IOException e) {
            return handleException(response, e);
        }
    }

    void getBoundsAsync(UrlImageSearchRequestData requestData,
                        AccountDataService accountDataService,
                        SyteCallback<BoundsResult> callback) {
        renewTimestamp();
        generateBoundsCall(requestData, accountDataService).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {
                try {
                    onBoundsResult(
                            requestData,
                            response,
                            requestData.getFirstBoundOffersCoordinates(),
                            accountDataService,
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

    SyteResult<OffersResult> getOffers(
            Bound bound,
            @Nullable CropCoordinates cropCoordinates,
            AccountDataService accountDataService
    ) {
        renewTimestamp();
        SyteResult<OffersResult> syteResult = new SyteResult<>();
        Response<ResponseBody> response = null;
        try {
            response = generateOffersCall(
                    bound.getOffersUrl(),
                    cropCoordinates,
                    accountDataService
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
            AccountDataService accountDataService,
            SyteCallback<OffersResult> callback) {
        renewTimestamp();
        generateOffersCall(
                bound.getOffersUrl(),
                cropCoordinates,
                accountDataService
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                SyteResult<OffersResult> syteResult = new SyteResult<>();
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
            UrlImageSearchRequestData requestData,
            Response<ResponseBody> response,
            @Nullable CropCoordinates cropCoordinates,
            AccountDataService accountDataService,
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

        if (requestData.isRetrieveOffersForTheFirstBound()) {
            if (sync) {
                Response<ResponseBody> offersResponse = null;
                try {
                    offersResponse = generateOffersCall(
                            syteResult.data.getBounds().get(0).getOffersUrl(),
                            cropCoordinates,
                            accountDataService
                    ).execute();
                } catch (IOException e) {
                    return handleException(response, e);
                }
                syteResult.data.setFirstBoundOffersResult(onOffersResult(offersResponse));
                return syteResult;
            } else {
                generateOffersCall(
                        syteResult.data.getBounds().get(0).getOffersUrl(),
                        cropCoordinates,
                        accountDataService
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        try {
                            syteResult.data.setFirstBoundOffersResult(onOffersResult(response));
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
            AccountDataService accountDataService
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
                cropEnabled ? accountDataService
                        .getData()
                        .getProducts()
                        .getSyteapp()
                        .getFeatures()
                        .getBoundingBox()
                        .getCropper()
                        .getCatalog() : null

        );
    }

    private OffersResult onOffersResult(Response<ResponseBody> offersResponse) throws IOException {
        if (offersResponse.isSuccessful()
                && offersResponse.body() != null) {

            String offersResponseString = offersResponse.body().string();
            OffersResult offersResult = new Gson().fromJson(
                    offersResponseString,
                    OffersResult.class
            );

            try {
                JSONObject jsonObject = new JSONObject(offersResponseString);
                JSONArray offersArray = jsonObject.getJSONArray("ads");
                for (int i = 0; i < offersResult.getOffers().size(); i++) {
                    offersResult.getOffers().get(i).setOriginalData(offersArray.getJSONObject(i)
                            .getJSONObject("original_data"));
                }
            } catch (Exception e) {
                handleException(offersResponse, e);
            }

            return offersResult;
        } else return null;
    }

    public SyteResult<BoundsResult> getBoundsWild(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService
    ) {

        ImageSearchClientImpl imageSearchClient = new ImageSearchClientImpl(
                this,
                accountDataService
        );
        UrlImageSearchRequestData urlImageSearchRequestData;
        try {
            urlImageSearchRequestData = prepareUrlImageSearchRequestData(
                    context,
                    requestData,
                    accountDataService
            );
        } catch (Exception e) {
            return handleException(null, e);
        }
        return imageSearchClient.getBounds(urlImageSearchRequestData);
    }

    void getBoundsWildAsync(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService,
            SyteCallback<BoundsResult> callback
    ) {
        ImageSearchClientImpl imageSearchClient = new ImageSearchClientImpl(
                this,
                accountDataService
        );
        prepareUrlImageSearchRequestDataAsync(
                context,
                requestData,
                accountDataService,
                new ExifRemovalResultCallback() {
                    @Override
                    public void onResult(UrlImageSearchRequestData requestData, Throwable e) {
                        if (e != null) {
                            callback.onResult(handleException(null, e));
                        } else {
                            imageSearchClient.getBoundsAsync(requestData, callback);
                        }
                    }
                }
        );
    }

    private UrlImageSearchRequestData prepareUrlImageSearchRequestData(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService
    ) throws IOException, JSONException, SyteGeneralException {

        byte[] bytes = prepareImage(context, requestData, accountDataService);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), bytes);

        Response<ResponseBody> response = mExifRemovalService.removeTags(
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                requestBody
        ).execute();
        ResponseBody body = response.body();

        if (body != null) {
            JSONObject jsonObject = new JSONObject(body.string());
            UrlImageSearchRequestData urlImageSearchRequestData = new UrlImageSearchRequestData(
                    jsonObject.getString("url"),
                    SyteProductType.DISCOVERY_BUTTON
            );
            urlImageSearchRequestData.setRetrieveOffersForTheFirstBound(
                    requestData.isRetrieveOffersForTheFirstBound()
            );
            urlImageSearchRequestData.setFirstBoundOffersCoordinates(
                    requestData.getFirstBoundOffersCoordinates()
            );
            urlImageSearchRequestData.setPersonalizedRanking(
                    requestData.getPersonalizedRanking()
            );
            return urlImageSearchRequestData;
        } else {
            throw new SyteGeneralException("Exif removal service returned empty body.");
        }
    }

    private void prepareUrlImageSearchRequestDataAsync(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService,
            ExifRemovalResultCallback callback
    ) {
        byte[] bytes = new byte[0];
        try {
            bytes = prepareImage(context, requestData, accountDataService);
        } catch (Exception e) {
            callback.onResult(null, e);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), bytes);

        mExifRemovalService.removeTags(
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                requestBody
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(body.string());
                        UrlImageSearchRequestData urlImageSearchRequestData = new UrlImageSearchRequestData(
                                jsonObject.getString("url"),
                                SyteProductType.DISCOVERY_BUTTON
                        );
                        urlImageSearchRequestData.setRetrieveOffersForTheFirstBound(
                                requestData.isRetrieveOffersForTheFirstBound()
                        );
                        urlImageSearchRequestData.setFirstBoundOffersCoordinates(
                                requestData.getFirstBoundOffersCoordinates()
                        );
                        callback.onResult(urlImageSearchRequestData, null);
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
    }

    private byte[] prepareImage(
            Context context,
            ImageSearchRequestData requestData,
            AccountDataService accountDataService) throws IOException {

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
                Utils.getImageScale(accountDataService)
        );

        SyteLogger.i(TAG, "Compressed image size: " + file.length() + " bytes");

        return Utils.getFileBytes(file);
    }

}
