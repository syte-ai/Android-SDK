package com.syte.ai.android_sdk.core;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RecommendationRemoteDataSource extends BaseRemoteDataSource {

    enum RecommendationProduct {
        SIMILAR_PRODUCTS("similars"),
        SHOP_THE_LOOK("ctl"),
        PERSONALIZATION("personalization");

        private final String mName;

        RecommendationProduct(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }
    }

    private final SyteService mSyteService;

    RecommendationRemoteDataSource(
            SyteService syteService,
            SyteConfiguration syteConfiguration
    ) {
        mConfiguration = syteConfiguration;
        mSyteService = syteService;
    }

    public SyteResult<SimilarProductsResult> getSimilarProducts(SimilarProductsRequestData similarProductsRequestData) {
        Response<ResponseBody> result = null;
        try {
            result =
                    generateSimilarsCall(similarProductsRequestData).execute();
            return onSimilarsResult(result);
        } catch (IOException | JSONException e) {
            return handleException(result, e);
        }
    }

    public void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback) {
        generateSimilarsCall(similarProductsRequestData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                try {
                    callback.onResult(onSimilarsResult(response));
                } catch (IOException | JSONException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }
        });
    }

    public SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLookRequestData shopTheLookRequestData,
            SytePlatformSettings sytePlatformSettings
    ) {
        Response<ResponseBody> result = null;
        try {
            result =
                    generateShopTheLookCall(shopTheLookRequestData).execute();
            return onShopTheLookResult(result, sytePlatformSettings);
        } catch (IOException | JSONException e) {
            return handleException(result, e);
        }
    }

    public void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            SytePlatformSettings sytePlatformSettings,
            SyteCallback<ShopTheLookResult> callback) {
        generateShopTheLookCall(shopTheLookRequestData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                try {
                    callback.onResult(onShopTheLookResult(response, sytePlatformSettings));
                } catch (IOException | JSONException e) {
                    callback.onResult(handleException(response, e));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                callback.onResult(handleOnFailure(t));
            }
        });
    }

    public SyteResult<PersonalizationResult> getPersonalization(PersonalizationRequestData personalizationRequestData) {
        Response<ResponseBody> result = null;
        try {
            result = generatePersonalizationCall(personalizationRequestData).execute();
            return onPersonalizationResult(result);
        } catch (IOException | JSONException | SyteWrongInputException e) {
            return handleException(result, e);
        }
    }

    public void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback) {
        try {
            generatePersonalizationCall(personalizationRequestData).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    try {
                        callback.onResult(onPersonalizationResult(response));
                    } catch (IOException | JSONException e) {
                        callback.onResult(handleException(response, e));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                    callback.onResult(handleOnFailure(t));
                }
            });
        } catch (SyteWrongInputException e) {
            callback.onResult(handleException(null, e));
        }
    }

    private SyteResult<SimilarProductsResult> onSimilarsResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();
        SimilarProductsResult similarsResult = new Gson().fromJson(
                responseString,
                SimilarProductsResult.class
        );

        // TODO put in a separate method
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray similarsArray = jsonObject.getJSONArray("response");
        for (int i = 0; i < similarsResult.getItems().size(); i++) {
            JSONObject offer = similarsArray.getJSONObject(i);
            if (offer.has("original_data")) {
                similarsResult
                        .getItems()
                        .get(i)
                        .setOriginalData(offer.getJSONObject("original_data"));
            }
        }

        SyteResult<SimilarProductsResult> syteResult = new SyteResult<>();
        syteResult.data = similarsResult;
        syteResult.isSuccessful = result.isSuccessful();
        syteResult.resultCode = result.code();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        }
        return syteResult;
    }

    private SyteResult<ShopTheLookResult> onShopTheLookResult(
            Response<ResponseBody> result,
            SytePlatformSettings sytePlatformSettings
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();
        ShopTheLookResult ctlResult = new Gson().fromJson(
                responseString,
                ShopTheLookResult.class
        );
        ctlResult.setSytePlatformSettings(sytePlatformSettings);

        // TODO put in a separate method
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray itemsArray = jsonObject.getJSONArray("response");
        for (int i = 0; i < ctlResult.getItems().size(); i++) {
            for (int j = 0; j < ctlResult.getItems().get(i).getOffers().size(); j++) {
                JSONObject offer = itemsArray.getJSONObject(i)
                        .getJSONArray("ads")
                        .getJSONObject(j);
                if (offer.has("original_data")) {
                    ctlResult
                            .getItems()
                            .get(i)
                            .getOffers()
                            .get(j)
                            .setOriginalData(offer.getJSONObject("original_data"));
                }
            }
        }

        SyteResult<ShopTheLookResult> syteResult = new SyteResult<>();
        syteResult.data = ctlResult;
        syteResult.isSuccessful = result.isSuccessful();
        syteResult.resultCode = result.code();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        }
        return syteResult;
    }

    private SyteResult<PersonalizationResult> onPersonalizationResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();

        PersonalizationResult personalizationResult = new Gson().fromJson(
                responseString,
                PersonalizationResult.class
        );

        // TODO put in a separate method
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray personalizationArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < personalizationResult.getItems().size(); i++) {
            JSONObject offer = personalizationArray.getJSONObject(i);
            if (offer.has("original_data")) {
                personalizationResult
                        .getItems()
                        .get(i)
                        .setOriginalData(offer.getJSONObject("original_data"));
            }
        }

        SyteResult<PersonalizationResult> syteResult = new SyteResult<>();
        syteResult.data = personalizationResult;
        syteResult.isSuccessful = result.isSuccessful();
        syteResult.resultCode = result.code();
        if (result.errorBody() != null) {
            syteResult.errorMessage = result.errorBody().string();
        }
        return syteResult;
    }

    private Call<ResponseBody> generateSimilarsCall(SimilarProductsRequestData similarProductsRequestData) {
        return mSyteService.getSimilars(
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                mConfiguration.getUserId(),
                Long.toString(mConfiguration.getSessionId()),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                mConfiguration.getLocale(),
                similarProductsRequestData.getFieldsToReturn().getName(),
                "sku:" + similarProductsRequestData.getSku(),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                similarProductsRequestData.getPersonalizedRanking() ?
                        mConfiguration.getSessionSkusString() : null,
                similarProductsRequestData.getLimit(),
                similarProductsRequestData.getSyteUrlReferer(),
                similarProductsRequestData.getImageUrl()
        );
    }

    private Call<ResponseBody> generateShopTheLookCall(ShopTheLookRequestData shopTheLookRequestData) {
        return mSyteService.getShopTheLook(
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                mConfiguration.getUserId(),
                Long.toString(mConfiguration.getSessionId()),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                mConfiguration.getLocale(),
                shopTheLookRequestData.getFieldsToReturn().getName(),
                "sku:" + shopTheLookRequestData.getSku(),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                shopTheLookRequestData.getPersonalizedRanking() ?
                        mConfiguration.getSessionSkusString() : null,
                shopTheLookRequestData.getLimit(),
                shopTheLookRequestData.getSyteUrlReferer(),
                shopTheLookRequestData.getLimitPerBound() == -1 ? null :
                        Integer.toString(shopTheLookRequestData.getLimitPerBound()),
                shopTheLookRequestData.getSyteOriginalItem(),
                shopTheLookRequestData.getImageUrl()
        );
    }

    private Call<ResponseBody> generatePersonalizationCall(PersonalizationRequestData personalizationRequestData) throws SyteWrongInputException {
        if (mConfiguration.getSessionSkusJSONArray() == null) {
            throw new SyteWrongInputException("There are no viewed products added. Can not proceed with the personalization call.");
        }
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body =
                RequestBody.create(mediaType,
                        "{\n    " +
                                    "\"user_uuid\": \"" + mConfiguration.getUserId() + "\",\n    " +
                                    "\"session_skus\": " + mConfiguration.getSessionSkusJSONArray() + ",\n    " +
                                    "\"model_version\": \"" + personalizationRequestData.getModelVersion() + "\"" +
                                "\n}");
        return mSyteService.getPersonalization(
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                RecommendationProduct.PERSONALIZATION.getName(),
                mConfiguration.getLocale(),
                personalizationRequestData.getFieldsToReturn().getName(),
                RecommendationProduct.PERSONALIZATION.getName(),
                RecommendationProduct.PERSONALIZATION.getName(),
                personalizationRequestData.getLimit(),
                personalizationRequestData.getSyteUrlReferer(),
                body
        );
    }

}
