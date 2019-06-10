package com.syte.ai.network

import com.google.gson.GsonBuilder
import com.syte.ai.data.AccountConfig
import com.syte.ai.data.ImageBounds
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Syte on 4/6/2019.
 *
 * A utility object to configure a singleton [HttpClient] instance.
 */
object HttpConfig {
    /**
     * The base URL for the Syte remote API.
     */
    const val BASE_URL = "https://syteapi.com/v1.1/"

    private val gson = GsonBuilder()
        .registerTypeAdapter(AccountConfig::class.java, AccountConfigResponseDeserializer())
        .registerTypeAdapter(Array<String>::class.java, com.syte.ai.network.CategoriesDeserializer())
        .registerTypeAdapter(ImageBounds::class.java, ImageBoundsResponseDeserializer())
        .create()
    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
    private var retrofit = builder.build()
    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
    private var syteHttpClient: HttpClient? = null

    /**
     * Initializes the [HttpClient] singleton if it was not already, and returns it.
     * It configures a custom timeout duration, and attaches a [logging interceptor][LoggingInterceptor]
     * on the requests made by this client.
     *
     * @return the singleton [HttpClient] instance.
     */
    fun createHttpClient(): HttpClient {
        return syteHttpClient ?: let {
            okHttpClient.connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build()
            okHttpClient.addInterceptor(logging)
                .addInterceptor(LoggingInterceptor())
            builder.client(okHttpClient.build())
            retrofit = builder.build()

            retrofit.create(HttpClient::class.java)
                .also {
                    syteHttpClient = it
                }
        }
    }
}