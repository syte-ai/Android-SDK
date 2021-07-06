package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.util.SyteLogger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

class EventsRemoteDataSource {

    private static final String TAG = EventsRemoteDataSource.class.getSimpleName();

    private static final String SYTE_EVENT_URL = "https://syteapi.com";
    private SyteConfiguration mConfiguration;
    private SyteEventService mService;

    EventsRemoteDataSource(SyteConfiguration configuration) {
        mConfiguration = configuration;
        Retrofit retrofit = RetrofitBuilder.build(SYTE_EVENT_URL);
        mService = retrofit.create(SyteEventService.class);
    }

    void fireEvent(BaseSyteEvent event) {
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(
                mediaType,
                event.getRequestBodyString() == null? "" : event.getRequestBodyString());
        mService.fireEvent(
                event.getTagsString(),
                event.getName(),
                mConfiguration.getAccountId(),
                mConfiguration.getSignature(),
                Long.toString(mConfiguration.getSessionId()),
                mConfiguration.getUserId(),
                event.getSyteUrlReferer(),
                body
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                SyteLogger.d(TAG, "Fire event response code - " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                SyteLogger.e(TAG, "Error while firing event: " + t.getMessage());
            }
        });
    }

}
