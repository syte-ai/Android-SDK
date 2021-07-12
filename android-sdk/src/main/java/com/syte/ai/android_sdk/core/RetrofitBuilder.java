package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.util.SyteLogger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

class RetrofitBuilder {

    static Retrofit build(String url) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (SyteLogger.getLogLevel().ordinal() < SyteLogger.LogLevel.WARN.ordinal()) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request requestWithUserAgent = originalRequest.newBuilder()
                                .header("User-Agent", "syte_android_sdk")
                                .build();
                        return chain.proceed(requestWithUserAgent);
                    }
                })
                .addInterceptor(interceptor)
                .build();
        return new Retrofit
                .Builder()
                .baseUrl(url)
                .client(client)
                .build();
    }

}
