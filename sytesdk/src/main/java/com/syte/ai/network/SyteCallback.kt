package com.syte.ai.network

import com.syte.ai.data.SyteResponse
import com.syte.ai.data.SyteResponse.Companion.error503Response
import com.syte.ai.data.SyteResponse.Companion.errorResponse
import com.syte.ai.data.SyteResponse.Companion.successResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Syte on 4/8/2019.
 *
 * A custom Retrofit callback.
 *
 * This callback invokes the passed-in callback function ([onComplete]) with a different [response][SyteResponse]
 * depending on the response's HTTP code.
 *
 * @param onComplete a completion listener to be notified with a [response from the remote Syte API][SyteResponse]
 */
class SyteCallback<T>(var onComplete: (response: SyteResponse<T>) -> Unit) : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        onComplete(error503Response(t.message ?: ""))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        val responseBody = response.body()
        val code = response.code()
        if (code == 200)
            onComplete(successResponse(responseBody!!, code))
        else
            onComplete(errorResponse(code, response.errorBody()?.string() ?: ""))
    }
}