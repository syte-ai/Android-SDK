package com.syte.ai.network

import com.syte.ai.data.AccountConfig
import com.syte.ai.data.ImageBounds
import com.syte.ai.data.OfferDetails
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

private const val AUTHORIZATION_HEADER =
    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaW5nZXIiOiI4R0hNVzNLZGM3QWQ3K3BFSDZ1MTJnPT0iLCJ0aW1lc3RhbXAiOjE1NTQ2NDA0MjY4MjAsInV1aWQiOiI2MmFkM2I3MC0yZmY0LTVhYTktODU1NS04N2VkNTVjMzVmOWYifQ.CBi1Ehm2KUlb_e6u2R4W120SnfKqvyxJtfPiMWwEXg4"

/**
 * Created by Syte on 4/6/2019.
 *
 * A Retrofit HTTP client interface that defines the endpoints to be used
 * when communicating with the Syte remote API.
 */
interface HttpClient {
    /**
     * Loads an account configuration.
     * @param url the URL of the endpoint from which to load the remote account config.
     */
    @GET
    fun getAccountConfig(@Url url: String): Call<AccountConfig>

    /**
     * Loads the list of categories.
     * @param url the URL of the endpoint from which to categories' list.
     */
    @GET
    fun getCategories(@Url url: String = "http://wearesyte.com/apiexample/force_cats.json")
            : Call<Array<String>>

    @POST("/offers/bb")
    fun getBoundsForImage(
        @Query("account_id") accountId: Int,
        @Query("feed") feed: String,
        @Query("sig") token: String,
        @Body body: RequestBody
    ): Call<ImageBounds>

    @POST("/offers/bb")
    fun getBoundsForImage(
        @Query("account_id") accountId: Int,
        @Query("feed") feed: String,
        @Query("sig") token: String,
        @Query("catalog") catalog: String,
        @Body body: RequestBody
    ): Call<ImageBounds>

    @POST("/offers/bb")
    fun getBoundsForBinaryImage(
        @Query("account_id") accountId: Int,
        @Query("feed") feed: String,
        @Query("sig") token: String,
        @Query("payload_type") payloadType: String = "image_bin",
        @Body body: RequestBody
    ): Call<ImageBounds>

    @POST("/offers/bb")
    fun getBoundsForBinaryImage(
        @Query("account_id") accountId: Int,
        @Query("feed") feed: String,
        @Query("sig") token: String,
        @Query("catalog") catalog: String,
        @Query("payload_type") payloadType: String = "image_bin",
        @Body body: RequestBody
    ): Call<ImageBounds>

    @GET
    fun getAdsForOffer(@Url offerUrl: String): Call<OfferDetails>

    @POST
    fun callAnalyticsApi(
        @Url url: String,
        @Header("Authorization") authorization: String = AUTHORIZATION_HEADER
    ): Call<Void>
}