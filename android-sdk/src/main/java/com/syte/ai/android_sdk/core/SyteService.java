package com.syte.ai.android_sdk.core;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SyteService {

    @GET("accounts/{accountId}")
    Call<ResponseBody> initialize(@Path("accountId") String accountId);

    @GET("v1.1/offers/bb")
    Call<ResponseBody> getBounds(@Query("account_id") String accountId,
                                 @Query("sig") String signature,
                                 @Query("syte_uuid") String userId,
                                 @Query("session_id") String sessionId,
                                 @Query("syte_app_ref") String syteAppRef,
                                 @Query("locale") String locale,
                                 @Query("catalog") String catalog,
                                 @Query("sku") String sku,
                                 @Query("imageUrl") String imageUrl,
                                 @Query("session_skus") String sessionSkus);

    @GET
    Call<ResponseBody> getOffers(
            @Url String offersUrl,
            @Query(value = "crop", encoded = true) String crop,
            @Query("force_cats") String forceCats,
            @Query("catalog") String catalog
    );

    @GET("v1.1/similars")
    Call<ResponseBody> getSimilars(@Query("account_id") String accountId,
                                   @Query("sig") String signature,
                                   @Query("syte_uuid") String userId,
                                   @Query("session_id") String sessionId,
                                   @Query("syte_app_ref") String syteAppRef,
                                   @Query("locale") String locale,
                                   @Query("fields") String fields,
                                   @Query("q") String sku,
                                   @Query("features") String features,
                                   @Query("syte_product") String product,
                                   @Query("session_skus") String sessionSkus,
                                   @Query("limit") int limit,
                                   @Query("syte_url_referer") String syteUrlReferer,
                                   @Query("imageUrl") String imageUrl);

    @GET("v1.1/similars")
    Call<ResponseBody> getShopTheLook(@Query("account_id") String accountId,
                                      @Query("sig") String signature,
                                      @Query("syte_uuid") String userId,
                                      @Query("session_id") String sessionId,
                                      @Query("syte_app_ref") String syteAppRef,
                                      @Query("locale") String locale,
                                      @Query("fields") String fields,
                                      @Query("q") String sku,
                                      @Query("features") String features,
                                      @Query("syte_product") String product,
                                      @Query("session_skus") String sessionSkus,
                                      @Query("limit") int limit,
                                      @Query("syte_url_referer") String syteUrlReferer,
                                      @Query("limit_per_bound") String limitPerBound,
                                      @Query("syte_original_item") String originalItem,
                                      @Query("imageUrl") String imageUrl);

    @POST("v1.1/personalization")
    Call<ResponseBody> getPersonalization(@Query("account_id") String accountId,
                                          @Query("sig") String signature,
                                          @Query("syte_app_ref") String syteAppRef,
                                          @Query("locale") String locale,
                                          @Query("fields") String fields,
                                          @Query("features") String features,
                                          @Query("syte_product") String product,
                                          @Query("limit") int limit,
                                          @Query("syte_url_referer") String syteUrlReferer,
                                          @Body RequestBody body);

}
