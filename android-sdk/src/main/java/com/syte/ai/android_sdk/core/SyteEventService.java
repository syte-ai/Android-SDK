package com.syte.ai.android_sdk.core;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SyteEventService {

    @POST("et")
    Call<ResponseBody> fireEvent(
            @Query("tags") String tags,
            @Query("name") String name,
            @Query("account_id") String accountId,
            @Query("sig") String signature,
            @Query("session_id") String sessionId,
            @Query("syte_uuid") String userId,
            @Query("syte_url_referer") String syteUrlReferer,
            @Body RequestBody body
    );

}
