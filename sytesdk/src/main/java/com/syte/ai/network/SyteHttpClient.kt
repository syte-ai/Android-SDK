package com.syte.ai.network

import com.syte.ai.data.AccountConfig
import com.syte.ai.data.ImageBounds
import com.syte.ai.data.OfferDetails
import com.syte.ai.data.SyteResponse
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Syte on 4/7/2019.
 *
 * This class serves as a wrapper around the [HttpClient] singleton instance.
 */
class SyteHttpClient(private val httpClient: HttpClient) {
    fun loadRemoteConfig(
        accountId: Int,
        onComplete: (response: SyteResponse<AccountConfig>) -> Unit = {}
    ) {
        httpClient.getAccountConfig("https://cdn.syteapi.com/accounts/$accountId")
            .enqueue(SyteCallback(onComplete))
    }

    fun loadCategories(onComplete: (response: SyteResponse<Array<String>>) -> Unit = {}) {
        httpClient.getCategories().enqueue(SyteCallback(onComplete))
    }

    fun getBoundsForImage(
        accountId: Int, feed: String, token: String, catalog: String, imageUrl: String,
        onComplete: (response: SyteResponse<ImageBounds>) -> Unit = {}
    ) {
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), "[\"$imageUrl\"]")
        if (catalog.isEmpty()) {
            httpClient.getBoundsForImage(accountId, feed, token, requestBody)
                .enqueue(SyteCallback(onComplete))
        } else {
            httpClient.getBoundsForImage(accountId, feed, token, catalog, requestBody)
                .enqueue(SyteCallback(onComplete))
        }
    }

    fun getBoundsForImageURI(
        accountId: Int, feed: String, token: String, catalog: String, requestBody: RequestBody,
        onComplete: (response: SyteResponse<ImageBounds>) -> Unit = {}
    ) {
        if (catalog.isEmpty()) {
            httpClient.getBoundsForBinaryImage(accountId = accountId, feed = feed, token = token, body = requestBody)
                .enqueue(SyteCallback(onComplete))
        } else {
            httpClient.getBoundsForImage(accountId, feed, token, catalog, requestBody)
                .enqueue(SyteCallback(onComplete))
        }
    }

    fun getAdsForOffer(
        offerUrl: String,
        onComplete: (response: SyteResponse<OfferDetails>) -> Unit = {}
    ) {
        httpClient.getAdsForOffer(offerUrl).enqueue(SyteCallback(onComplete))
    }

    fun callAnalytics(url: String, onComplete: (response: SyteResponse<Void>) -> Unit = {}) {
        httpClient.callAnalyticsApi(url).enqueue(SyteCallback(onComplete))
    }
}