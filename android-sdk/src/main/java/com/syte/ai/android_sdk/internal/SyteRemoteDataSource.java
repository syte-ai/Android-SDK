package com.syte.ai.android_sdk.internal;

import com.google.gson.Gson;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SyteRemoteDataSource extends BaseRemoteDataSource {

    private interface BoundsResultCallback {
        void onResult(SyteResult<BoundsResult> result);
    }

    private final SyteService mSyteService;

    SyteRemoteDataSource(SyteConfiguration configuration) {
        super(configuration);
        mSyteService = mRetrofit.create(SyteService.class);
    }

    @Override
    protected void startCountDown() {

    }

    @Override
    protected void resetTimer() {

    }

    @Override
    SyteResult<AccountDataService> initialize() {
        Response<ResponseBody> response = null;
        SyteResult<AccountDataService> syteResult = new SyteResult<>();
        try {
            response = mSyteService.initialize(mConfiguration.getAccountId()).execute();
        } catch (IOException e) {
            //TODO handle error here
            syteResult.isSuccessful = false;
            return syteResult;
        }
        try {
            syteResult.data =
                    new Gson().fromJson(response.body().string(), AccountDataService.class);
        } catch (IOException e) {
            //TODO handle error here
        }
        syteResult.resultCode = response.code();
        syteResult.isSuccessful = response.isSuccessful();
        return syteResult;
    }

    @Override
    void initializeAsync(SyteCallback<AccountDataService> callback) {
        mSyteService.initialize(mConfiguration.getAccountId()).enqueue(
                new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        SyteResult<AccountDataService> syteResult = new SyteResult<>();
                        try {
                            syteResult.data =
                                    new Gson().fromJson(response.body().string(), AccountDataService.class);
                        } catch (IOException e) {
                            //TODO handle error here
                        }
                        syteResult.resultCode = response.code();
                        syteResult.isSuccessful = response.isSuccessful();
                        callback.onResult(syteResult);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        SyteResult<AccountDataService> syteResult = new SyteResult<>();
                        syteResult.data = null;
                        syteResult.isSuccessful = false;
                        callback.onResult(syteResult);
                    }
                }
        );
    }

    @Override
    SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData requestData, AccountDataService accountDataService) {
        try {
            return onBoundsResult(requestData,
                    mSyteService.getBounds(
                            mConfiguration.getAccountId(),
                            mConfiguration.getSignature(),
                            mConfiguration.getUserId(),
                            Long.toString(mConfiguration.getSessionId()),
                            requestData.getProductType().name,
                            mConfiguration.getLocale(),
                            accountDataService
                                    .getData()
                                    .getProducts()
                                    .getSyteapp()
                                    .getFeatures()
                                    .getBoundingBox()
                                    .getCropper()
                                    .getCatalog(),
                            requestData.getSku(),
                            requestData.getImageUrl()
                    ).execute(),
                    true,
                    null
            );
        } catch (IOException e) {
            //TODO handle error here
            SyteResult<BoundsResult> result = new SyteResult<>();
            result.isSuccessful = false;
            return result;
        }
    }

    @Override
    void getBoundsAsync(UrlImageSearchRequestData requestData,
                        AccountDataService accountDataService,
                        SyteCallback<BoundsResult> callback) {

        mSyteService.getBounds(
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                mConfiguration.getUserId(),
                Long.toString(mConfiguration.getSessionId()),
                requestData.getProductType().name,
                mConfiguration.getLocale(),
                accountDataService
                        .getData()
                        .getProducts()
                        .getSyteapp()
                        .getFeatures()
                        .getBoundingBox()
                        .getCropper()
                        .getCatalog(),
                requestData.getSku(),
                requestData.getImageUrl()
        ).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NotNull Call<ResponseBody> call,
                                   @NotNull Response<ResponseBody> response) {
                try {
                    onBoundsResult(requestData, response, false, new BoundsResultCallback() {
                        @Override
                        public void onResult(SyteResult<BoundsResult> result) {
                            callback.onResult(result);
                        }
                    });
                } catch (IOException e) {
                    //TODO handle error here
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, Throwable t) {
                //TODO handle error here
            }

        });
    }

    @Override
    SyteResult<OffersResult> getOffers(Bound bound) {
        SyteResult<OffersResult> syteResult = new SyteResult<>();
        try {
            Response<ResponseBody> response = mSyteService.getOffers(bound.getOffersUrl()).execute();
            syteResult.isSuccessful = response.isSuccessful();
            syteResult.resultCode = response.code();
            syteResult.data = onOffersResult(response);
            return syteResult;
        } catch (IOException e) {
            syteResult.isSuccessful = false;
            return syteResult;
        }
    }

    @Override
    void getOffersAsync(Bound bound, SyteCallback<OffersResult> callback) {
        mSyteService.getOffers(bound.getOffersUrl()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                SyteResult<OffersResult> syteResult = new SyteResult<>();
                syteResult.isSuccessful = response.isSuccessful();
                syteResult.resultCode = response.code();
                try {
                    syteResult.data = onOffersResult(response);
                    callback.onResult(syteResult);
                } catch (IOException e) {
                    //TODO handle error here
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //TODO handle error here
            }
        });
    }

    private SyteResult<BoundsResult> onBoundsResult(
            UrlImageSearchRequestData requestData,
            Response<ResponseBody> response,
            boolean sync,
            BoundsResultCallback resultCallback
    ) throws IOException {
        SyteResult<BoundsResult> syteResult = new SyteResult<>();
        syteResult.isSuccessful = response.isSuccessful();
        syteResult.resultCode = response.code();

        if (response.body() == null) {
            //TODO: handle error here
        }

        String modifiedResponseString = null;
        try {
            modifiedResponseString =
                    response.body().string().replace(requestData.getImageUrl(), "bounds");
        } catch (IOException e) {
        }

        if (modifiedResponseString == null) {
            //TODO: handle error here
        }

        syteResult.data = new Gson().fromJson(modifiedResponseString, BoundsResult.class);

        if (syteResult.data.getBounds() == null || syteResult.data.getBounds().size() == 0) {
            resultCallback.onResult(syteResult);
        }

        if (requestData.isRetrieveOffersForTheFirstBound()) {
            if (sync) {
                Response<ResponseBody> offersResponse =
                        mSyteService.getOffers(
                                syteResult.data.getBounds().get(0).getOffersUrl()
                        ).execute();
                syteResult.data.setFirstBoundOffersResult(onOffersResult(offersResponse));
                return syteResult;
            } else {
                mSyteService.getOffers(
                        syteResult.data.getBounds().get(0).getOffersUrl()
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            syteResult.data.setFirstBoundOffersResult(onOffersResult(response));
                            if (resultCallback != null) {
                                resultCallback.onResult(syteResult);
                            }
                        } catch (IOException e) {
                            //TODO: handle error here
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //TODO handle error here
                    }
                });
            }
        }
        if (resultCallback != null) {
            resultCallback.onResult(syteResult);
        }
        return syteResult;
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
            } catch (JSONException e) {
                //TODO handle error here
            }

            return offersResult;
        } else return null;
    }

    @Override
    void wildImageSearch(ImageSearchRequestData requestData) {

    }

    @Override
    void wildImageSearchAsync(ImageSearchRequestData requestData, SyteCallback callback) {

    }

}
