package com.syte.ai.android_sdk.internal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SyteService {

    @GET("accounts/{accountId}")
    Call<ResponseBody> initialize(@Path("accountId") String accountId);


    //TODO include session SKUs here
    @GET("v1.1/offers/bb")
    Call<ResponseBody> getBounds(@Query("account_id") String accountId,
                                 @Query("sig") String signature,
                                 @Query("syte_uuid") String userId,
                                 @Query("session_id") String sessionId,
                                 @Query("syte_app_ref") String syteAppRef,
                                 @Query("locale") String locale,
                                 @Query("catalog") String catalog,
                                 @Query("sku") String sku,
                                 @Query("imageUrl") String imageUrl);

    @GET
    Call<ResponseBody> getOffers(
            @Url String offersUrl,
            @Query(value = "crop", encoded = true) String crop,
            @Query("force_cats") String forceCats,
            @Query("catalog") String catalog
    );

}
