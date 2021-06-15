package com.syte.ai.android_sdk.internal;

import com.syte.ai.android_sdk.data.result.account.AccountDataService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SyteService {

    @GET("accounts/{accountId}")
    Call<AccountDataService> initialize(@Path("accountId") String accountId);

}
