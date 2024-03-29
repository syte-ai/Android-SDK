package com.syte.ai.android_sdk.core;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarItems;
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

    public SyteResult<SimilarProductsResult> getSimilarProducts(SimilarItems similarProducts) {
        Response<ResponseBody> result = null;
        try {
            result =
                    generateSimilarsCall(similarProducts).execute();
            return onSimilarsResult(result);
        } catch (IOException | JSONException e) {
            return handleException(result, e);
        }
    }

    public void getSimilarProductsAsync(
            SimilarItems similarProducts,
            SyteCallback<SimilarProductsResult> callback) {
        generateSimilarsCall(similarProducts).enqueue(new Callback<ResponseBody>() {
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
            ShopTheLook shopTheLook
    ) {
        Response<ResponseBody> result = null;
        try {
            result =
                    generateShopTheLookCall(shopTheLook).execute();
            return onShopTheLookResult(result);
        } catch (IOException | JSONException e) {
            return handleException(result, e);
        }
    }

    public void getShopTheLookAsync(
            ShopTheLook shopTheLook,
            SyteCallback<ShopTheLookResult> callback) {
        generateShopTheLookCall(shopTheLook).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                try {
                    callback.onResult(onShopTheLookResult(response));
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

    public SyteResult<PersonalizationResult> getPersonalization(Personalization personalization) {
        Response<ResponseBody> result = null;
        try {
            result = generatePersonalizationCall(personalization).execute();
            return onPersonalizationResult(result);
        } catch (IOException | JSONException | SyteWrongInputException e) {
            return handleException(result, e);
        }
    }

    public void getPersonalizationAsync(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback) {
        try {
            generatePersonalizationCall(personalization).enqueue(new Callback<ResponseBody>() {
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
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            return handleEmptyBody(result);
        }

        String responseString = result.body().string();
        ShopTheLookResult ctlResult = new Gson().fromJson(
                responseString,
                ShopTheLookResult.class
        );

        // TODO put in a separate method
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray itemsArray = jsonObject.getJSONArray("response");
        for (int i = 0; i < ctlResult.getItems().size(); i++) {
            for (int j = 0; j < ctlResult.getItems().get(i).getItems().size(); j++) {
                JSONObject offer = itemsArray.getJSONObject(i)
                        .getJSONArray("ads")
                        .getJSONObject(j);
                if (offer.has("original_data")) {
                    ctlResult
                            .getItems()
                            .get(i)
                            .getItems()
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

    private Call<ResponseBody> generateSimilarsCall(SimilarItems similarProducts) {
        Long sessionId = mConfiguration.getSessionId();
        String stringSessionId = "";
        if (sessionId == -1L) {
            stringSessionId = "OptedOut";
        } else {
            stringSessionId = Long.toString(sessionId);
        }
        return mSyteService.getSimilars(
                mConfiguration.getAccountId(),
                mConfiguration.getApiSignature(),
                similarProducts.getPersonalizedRanking() ? mConfiguration.getUserId() : null,
                similarProducts.getPersonalizedRanking() ? stringSessionId : null,
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                mConfiguration.getLocale(),
                similarProducts.getFieldsToReturn().getName(),
                "sku:" + similarProducts.getSku(),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                similarProducts.getPersonalizedRanking() && mConfiguration.isLocalStorageEnabled() ?
                        Utils.viewedProductsString(mConfiguration.getViewedProducts()) : null,
                similarProducts.getLimit(),
                similarProducts.getSyteUrlReferer(),
                similarProducts.getImageUrl(),
                similarProducts.getOptions()
        );
    }

    private Call<ResponseBody> generateShopTheLookCall(ShopTheLook shopTheLook) {
        Long sessionId = mConfiguration.getSessionId();
        String stringSessionId = "";
        if (sessionId == -1L) {
            stringSessionId = "OptedOut";
        } else {
            stringSessionId = Long.toString(sessionId);
        }
        return mSyteService.getShopTheLook(
                mConfiguration.getAccountId(),
                mConfiguration.getApiSignature(),
                shopTheLook.getPersonalizedRanking() ? mConfiguration.getUserId() : null,
                shopTheLook.getPersonalizedRanking() ? stringSessionId : null,
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                mConfiguration.getLocale(),
                shopTheLook.getFieldsToReturn().getName(),
                "sku:" + shopTheLook.getSku(),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                shopTheLook.getPersonalizedRanking() && mConfiguration.isLocalStorageEnabled() ?
                        Utils.viewedProductsString(mConfiguration.getViewedProducts()) : null,
                shopTheLook.getLimit(),
                shopTheLook.getSyteUrlReferer(),
                shopTheLook.getLimitPerBound() == -1 ? null :
                        Integer.toString(shopTheLook.getLimitPerBound()),
                shopTheLook.getSyteOriginalItem(),
                shopTheLook.getImageUrl(),
                shopTheLook.getOptions()
        );
    }

    private Call<ResponseBody> generatePersonalizationCall(Personalization personalization) throws SyteWrongInputException {
        if (Utils.viewedProductsJSONArray(mConfiguration.getViewedProducts()) == null
                && Utils.viewedProductsJSONArray(personalization.getSku()) == null) {
            throw new SyteWrongInputException("There are no viewed products added. Can not proceed with the personalization call.");
        }
        String viewedProducts = Utils.viewedProductsJSONArray(mConfiguration.getViewedProducts());
        viewedProducts = viewedProducts == null ? Utils.viewedProductsJSONArray(personalization.getSku()) : viewedProducts;

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body =
                RequestBody.create(mediaType,
                        "{\n    " +
                                    "\"user_uuid\": \"" + mConfiguration.getUserId() + "\",\n    " +
                                    "\"session_skus\": " + viewedProducts + ",\n    " +
                                    "\"model_version\": \"" + personalization.getModelVersion() + "\"" +
                                "\n}");
        return mSyteService.getPersonalization(
                mConfiguration.getAccountId(),
                mConfiguration.getApiSignature(),
                RecommendationProduct.PERSONALIZATION.getName(),
                mConfiguration.getLocale(),
                personalization.getFieldsToReturn().getName(),
                RecommendationProduct.PERSONALIZATION.getName(),
                RecommendationProduct.PERSONALIZATION.getName(),
                personalization.getLimit(),
                personalization.getSyteUrlReferer(),
                body,
                personalization.getOptions()
        );
    }

}
