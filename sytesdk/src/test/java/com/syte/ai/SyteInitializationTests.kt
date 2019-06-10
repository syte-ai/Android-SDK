package com.syte.ai

import android.content.Context
import com.syte.ai.data.AccountConfig
import com.syte.ai.exceptions.SyteInitializationException
import com.syte.ai.sytesdk.network.HttpClient
import com.syte.ai.sytesdk.network.SyteHttpClient
import com.syte.ai.sytesdk.network.TestNetworkMonitor
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Created by Syte on 4/7/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class SyteInitializationTests {

    @Captor
    lateinit var captor: ArgumentCaptor<String>
    @Mock
    lateinit var mockContext: Context
    @Mock
    lateinit var mHttpClient: HttpClient
    @Mock
    lateinit var mSyteHttpClient: SyteHttpClient
    @Mock
    lateinit var mockConfigCall: Call<com.syte.ai.data.AccountConfig>

    lateinit var testMonitor: TestNetworkMonitor

    @Before
    fun setUp() {
        testMonitor = TestNetworkMonitor()
    }

    @Test
    fun callingInitWithInvalidAccountIdThrowsException() {
        assertFailsWith<com.syte.ai.exceptions.SyteInitializationException> {
            com.syte.ai.Syte.init(mockContext, 0, "", mSyteHttpClient, testMonitor)
        }
    }

    @Test
    fun callingInitWithEmptyTokenThrowsException() {
        assertFailsWith<com.syte.ai.exceptions.SyteInitializationException> {
            com.syte.ai.Syte.init(mockContext, [your_account_id], "", mSyteHttpClient, testMonitor)
        }
    }

    @Test
    @Ignore
    fun loadingARemoteConfigUpdatesTheLocalConfig() {
        val accountId = 100
        val features = arrayOf("feature1", "feature2")

        val mockedResponse =
            Response.success(200, com.syte.ai.data.AccountConfig(accountId, features))
        `when`(mHttpClient.getAccountConfig(any())).thenReturn(mockConfigCall)
        doAnswer {
            val callback = it.getArgument<Callback<com.syte.ai.data.AccountConfig>>(0)
            callback.onResponse(mockConfigCall, mockedResponse)
            null
        }.`when`(mockConfigCall).enqueue(any())

        mSyteHttpClient = SyteHttpClient(mHttpClient)
        com.syte.ai.Syte.init(mockContext, accountId, "token", mSyteHttpClient, testMonitor)
        /*Syte.registerCallback {
            assertArrayEquals(features, it.mConfig.features)
            verify(mHttpClient).getAccountConfig(capture(captor))
            assertEquals("https://cdn.syteapi.com/accounts/100", captor.allValues[0])
        }*/
    }

    @Test
    fun loadingRemoteConfigWithNoConnectivityDoesNothing() {
        testMonitor.isConnected = false
        com.syte.ai.Syte.init(mockContext, [your_account_id], "token", mSyteHttpClient, testMonitor)
        verifyZeroInteractions(mSyteHttpClient)
    }

    @After
    fun tearDown() {
        com.syte.ai.Syte.reset()
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}