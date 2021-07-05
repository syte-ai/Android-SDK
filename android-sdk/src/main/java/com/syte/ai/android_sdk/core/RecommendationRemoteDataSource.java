package com.syte.ai.android_sdk.core;

import android.util.Log;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;

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

class RecommendationRemoteDataSource {

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


    private SyteConfiguration mSyteConfiguration;
    private SyteService mSyteService;

    RecommendationRemoteDataSource(
            SyteService syteService,
            SyteConfiguration syteConfiguration
    ) {
        mSyteConfiguration = syteConfiguration;
        mSyteService = syteService;
    }

    public void setConfiguration(SyteConfiguration syteConfiguration) {
        mSyteConfiguration = syteConfiguration;
    }

    public SyteResult<SimilarProductsResult> getSimilarProducts(SimilarProductsRequestData similarProductsRequestData) {

        try {
            Response<ResponseBody> result =
                    generateSimilarsCall(similarProductsRequestData).execute();
            return onSimilarsResult(result);
        } catch (IOException | JSONException e) {
            //TODO handle error
        }

        SyteResult<SimilarProductsResult> syteResult = new SyteResult<>();
        syteResult.isSuccessful = false;
        syteResult.data = null;
        return syteResult;
    }

    public void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback) {
        generateSimilarsCall(similarProductsRequestData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResult(onSimilarsResult(response));
                } catch (IOException | JSONException e) {
                    //TODO handle error
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SyteResult<SimilarProductsResult> syteResult = new SyteResult<>();
                syteResult.isSuccessful = false;
                syteResult.data = null;
                callback.onResult(syteResult);
            }
        });
    }

    public SyteResult<ShopTheLookResult> getShopTheLook(ShopTheLookRequestData shopTheLookRequestData) {

        try {
            Response<ResponseBody> result =
                    generateShopTheLookCall(shopTheLookRequestData).execute();
            return onShopTheLookResult(result);
        } catch (IOException | JSONException e) {
            //TODO handle error
        }

        SyteResult<ShopTheLookResult> syteResult = new SyteResult<>();
        syteResult.isSuccessful = false;
        syteResult.data = null;
        return syteResult;
    }

    public void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            SyteCallback<ShopTheLookResult> callback) {
        generateShopTheLookCall(shopTheLookRequestData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResult(onShopTheLookResult(response));
                } catch (IOException | JSONException e) {
                    //TODO handle error
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SyteResult<ShopTheLookResult> syteResult = new SyteResult<>();
                syteResult.isSuccessful = false;
                syteResult.data = null;
                callback.onResult(syteResult);
            }
        });
    }

    public SyteResult<PersonalizationResult> getPersonalization(PersonalizationRequestData personalizationRequestData) {

        try {
            Response<ResponseBody> result =
                    generatePersonalizationCall(personalizationRequestData).execute();
            return onPersonalizationResult(result);
        } catch (IOException | JSONException e) {
            //TODO handle error
        }

        SyteResult<PersonalizationResult> syteResult = new SyteResult<>();
        syteResult.isSuccessful = false;
        syteResult.data = null;
        return syteResult;
    }

    public void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback) {
        generatePersonalizationCall(personalizationRequestData).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    callback.onResult(onPersonalizationResult(response));
                } catch (IOException | JSONException e) {
                    //TODO handle error
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SyteResult<PersonalizationResult> syteResult = new SyteResult<>();
                syteResult.isSuccessful = false;
                syteResult.data = null;
                callback.onResult(syteResult);
            }
        });
    }

    private SyteResult<SimilarProductsResult> onSimilarsResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            // TODO handle error here
        }

        String responseString = result.body().string();
        SimilarProductsResult similarsResult = new Gson().fromJson(
                responseString,
                SimilarProductsResult.class
        );

        // TODO put in a separate method
        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray similarsArray = jsonObject.getJSONArray("response");
        for (int i = 0; i < similarsResult.getSimilars().size(); i++) {
            JSONObject offer = similarsArray.getJSONObject(i);
            if (offer.has("original_data")) {
                similarsResult
                        .getSimilars()
                        .get(i)
                        .setOriginalData(offer.getJSONObject("original_data"));
            }
        }

        SyteResult<SimilarProductsResult> syteResult = new SyteResult<>();
        syteResult.data = similarsResult;
        syteResult.isSuccessful = result.isSuccessful();
        syteResult.resultCode = result.code();
        return syteResult;
    }

    private SyteResult<ShopTheLookResult> onShopTheLookResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            // TODO handle error here
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
        return syteResult;
    }

    private SyteResult<PersonalizationResult> onPersonalizationResult(
            Response<ResponseBody> result
    ) throws IOException, JSONException {
        if (result.body() == null) {
            // TODO handle error here
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
        return syteResult;
    }

    private Call<ResponseBody> generateSimilarsCall(SimilarProductsRequestData similarProductsRequestData) {
        return mSyteService.getSimilars(
                mSyteConfiguration.getAccountId(),
                mSyteConfiguration.getSignature(),
                mSyteConfiguration.getUserId(),
                Long.toString(mSyteConfiguration.getSessionId()),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                mSyteConfiguration.getLocale(),
                similarProductsRequestData.getFieldsToReturn().getName(),
                "sku:" + similarProductsRequestData.getSku(),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                RecommendationProduct.SIMILAR_PRODUCTS.getName(),
                similarProductsRequestData.getPersonalizedRanking() ?
                        mSyteConfiguration.getSessionSkusString() : null,
                similarProductsRequestData.getLimit(),
                similarProductsRequestData.getSyteUrlReferer(),
                similarProductsRequestData.getImageUrl()
        );
    }

    private Call<ResponseBody> generateShopTheLookCall(ShopTheLookRequestData shopTheLookRequestData) {
        return mSyteService.getShopTheLook(
                mSyteConfiguration.getAccountId(),
                mSyteConfiguration.getSignature(),
                mSyteConfiguration.getUserId(),
                Long.toString(mSyteConfiguration.getSessionId()),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                mSyteConfiguration.getLocale(),
                shopTheLookRequestData.getFieldsToReturn().getName(),
                "sku:" + shopTheLookRequestData.getSku(),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                RecommendationProduct.SHOP_THE_LOOK.getName(),
                shopTheLookRequestData.getPersonalizedRanking() ?
                        mSyteConfiguration.getSessionSkusString() : null,
                shopTheLookRequestData.getLimit(),
                shopTheLookRequestData.getSyteUrlReferer(),
                shopTheLookRequestData.getLimitPerBound() == -1 ? null :
                        Integer.toString(shopTheLookRequestData.getLimitPerBound()),
                shopTheLookRequestData.getSyteOriginalItem(),
                shopTheLookRequestData.getImageUrl()
        );
    }

    private Call<ResponseBody> generatePersonalizationCall(PersonalizationRequestData personalizationRequestData) {
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body =
                RequestBody.create(mediaType,
                        "{\n    " +
                                    "\"user_uuid\": \"" + mSyteConfiguration.getUserId() + "\",\n    " +
                                    "\"session_skus\": " + mSyteConfiguration.getSessionSkusJSONArray() + ",\n    " +
                                    "\"model_version\": \"" + personalizationRequestData.getModelVersion() + "\"" +
                                "\n}");
        return mSyteService.getPersonalization(
                mSyteConfiguration.getAccountId(),
                mSyteConfiguration.getSignature(),
                RecommendationProduct.PERSONALIZATION.getName(),
                mSyteConfiguration.getLocale(),
                personalizationRequestData.getFieldsToReturn().getName(),
                RecommendationProduct.PERSONALIZATION.getName(),
                RecommendationProduct.PERSONALIZATION.getName(),
                personalizationRequestData.getLimit(),
                personalizationRequestData.getSyteUrlReferer(),
                body
        );
    }

}
