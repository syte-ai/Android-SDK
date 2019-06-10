package com.syte.ai.network

import com.syte.ai.utils.SyteLogger.logFailure
import com.syte.ai.utils.SyteLogger.logStart
import com.syte.ai.utils.SyteLogger.logSuccess
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by Syte on 4/7/2019.
 *
 * A custom logging interceptor.
 *
 * It logs the request's target URL before sending it, and logs the response's body once it is received.
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        logStart(request.url().toString())
        val response = chain.proceed(request)

        if (response.code() == 200) {
            try {
                val contentType = response.body()!!.contentType()
                val content = response.body()?.string()
                logSuccess(content ?: "EMPTY_RESPONSE")

                val wrappedBody = ResponseBody.create(contentType, content ?: "")
                return response.newBuilder().body(wrappedBody).build()
            } catch (e: Exception) {
                logSuccess(response.toString())
            }
        } else
            logFailure(response.toString())

        return response
    }
}