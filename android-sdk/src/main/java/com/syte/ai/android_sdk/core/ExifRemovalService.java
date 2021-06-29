package com.syte.ai.android_sdk.core;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ExifRemovalService {

    @PUT("align")
    Call<ResponseBody> removeTags(
            @Query("account_id") String accountId,
            @Query("sig") String signature,
            @Body RequestBody imagePayload
    );

}
