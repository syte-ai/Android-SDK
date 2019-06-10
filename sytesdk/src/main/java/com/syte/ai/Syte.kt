package com.syte.ai

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import com.syte.ai.Syte.Config.Companion.verifyCatalog
import com.syte.ai.Syte.Config.Companion.verifyCategory
import com.syte.ai.Syte.Config.Companion.verifyCurrency
import com.syte.ai.Syte.Config.Companion.verifyGender
import com.syte.ai.data.ImageBounds
import com.syte.ai.data.OfferDetails
import com.syte.ai.data.SyteResponse
import com.syte.ai.data.SyteResponse.Companion.noNetworkConnectionResponse
import com.syte.ai.exceptions.*
import com.syte.ai.network.AndroidNetworkMonitor
import com.syte.ai.network.HttpConfig
import com.syte.ai.network.NetworkMonitor
import com.syte.ai.network.SyteHttpClient
import com.syte.ai.utils.SyteLogger
import com.syte.ai.utils.SyteLogger.logNetworkUnavailable
import com.syte.ai.utils.generateSessionId
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Syte on 4/6/2019.
 */
@Suppress("NAME_SHADOWING")
class Syte private constructor(
    internal var mContext: Context? = null,
    internal var mConfig: Config = Config(),
    internal var mHttpClient: SyteHttpClient,
    internal var mNetworkMonitor: NetworkMonitor
) {

    companion object {
        /**
         * The singleton instance.
         */
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var syte: Syte? = null
        /**
         * A boolean flag indicating whether the SDK is currently initializing or not.
         */
        @Volatile
        private var isInitializing = false
        /**
         * THe list of categories to be loaded from the remote API.
         */
        @Volatile
        private var categoriesList = arrayOf<String>()

        /**
         * Callback functions to be triggered when the Syte SDK is ready to be used.
         *
         * They will trigger when the SDK is fully initialized and a remote configuration is loaded.
         */
        @Volatile
        private var onSyteReadyCallbacks: ArrayList<() -> Unit> = arrayListOf()

        /**
         * Initializes the SDK.
         * Sets the ID of the developer account and the associated token
         * used to make calls to the Syte APIs.
         *
         * @param appContext The app context needed to get the active network status
         * @param accountId The Syte developer account ID
         * @param token The Syte developer account token
         */
        fun init(appContext: Context, accountId: Int, token: String) {
            init(
                appContext,
                accountId,
                token,
                SyteHttpClient(HttpConfig.createHttpClient())
            )
        }

        /**
         * Initializes the SDK.
         * Sets the ID of the developer account and the associated token
         * used to make calls to the Syte APIs.
         *
         * @param appContext The app context needed to get the active network status
         * @param accountId The Syte developer account ID
         * @param token The Syte developer account token
         * @param client The Syte HTTP client through which API calls will be made
         */
        internal fun init(appContext: Context, accountId: Int, token: String, client: SyteHttpClient) {
            init(
                appContext,
                accountId,
                token,
                client,
                AndroidNetworkMonitor(appContext.applicationContext)
            )
        }

        /**
         * Initializes the SDK.
         * Sets the ID of the developer account and the associated token
         * used to make calls to the Syte APIs.
         *
         * @param appContext The app context needed to get the active network status
         * @param accountId The Syte developer account ID
         * @param token The Syte developer account token
         * @param client The Syte HTTP client through which API calls will be made
         * @param networkMonitor The network monitor in charge of fetching the active network status
         */
        internal fun init(
            appContext: Context, accountId: Int, token: String,
            client: SyteHttpClient, networkMonitor: NetworkMonitor
        ) {
            if (!isInitializing) {
                if (accountId <= 0)
                    throw SyteInitializationException("init() was called with an invalid account ID.")
                if (token.isEmpty())
                    throw SyteInitializationException("init() was called with an empty token.")

                initializeSyte(appContext, accountId, token, client, networkMonitor)
            }
        }

        /**
         * Sets a global flag to enable/disable console logs.
         *
         * @param toggle a boolean flag that indicates whether to enable or disable logging to the console.
         */
        fun setDebugMode(toggle: Boolean) {
            SyteLogger.isEnabled = false
        }

        /**
         * Alters the current SDK's configuration.
         * Passing in a value of null for a given parameter will make the SDK ignore configuring it.
         *
         * @param catalog the new catalog configuration
         * @param currency the new currency configuration
         * @param gender the new gender configuration
         * @param category the new category configuration
         */
        @Synchronized
        fun modifyConfig(
            catalog: Array<String>? = null, currency: String? = null,
            gender: String? = null, category: String? = null
        ) {
            if (syte == null) {
                throw IllegalStateException(
                    "Cannot modify config - Initialize the SDK first by calling init() or register a readiness callback"
                )
            }

            val config = syte!!.mConfig
            if (catalog != null) {
                verifyCatalog(catalog)
                config.catalog = catalog
            }
            if (currency != null) {
                verifyCurrency(currency)
                config.currency = currency
            }
            if (gender != null) {
                verifyGender(gender)
                config.gender = gender
            }
            if (category != null) {
                verifyCategory(category)
                config.category = category
            }
        }

        /**
         * Retrieves the bounds for an image with the specified URL.
         * It also makes a call to the analytics API to log the operation.
         *
         * @param imageUrl the URL of the image whose bounds are to be fetched.
         * @param onComplete a callback function to be invoked with the response.
         */
        fun getBoundsForImage(
            imageUrl: String,
            onComplete: (response: SyteResponse<ImageBounds>) -> Unit
        ) {
            if (Syte.Companion.syte == null) {
                throw IllegalStateException(
                    "Cannot fetch image bounds - you must initialize the SDK first by calling init()."
                )
            }

            Syte.Companion.syte!!.apply {
                callAnalytics("bounds")
                getImageBounds(imageUrl, onComplete)
            }
        }

        /**
         * Retrieves the bounds for an image with the specified [Uri].
         * It also makes a call to the analytics API to log the operation.
         *
         * @param imageUri the [URI][Uri] of the image whose bounds are to be fetched.
         * @param onComplete a callback function to be invoked with the response.
         * @throws NotAnImageException in case the provided image URI was not pointing to a valid image file.
         */
        @Throws(NotAnImageException::class)
        fun getBoundsForImage(
            imageUri: Uri,
            onComplete: (response: SyteResponse<ImageBounds>) -> Unit
        ) {
            if (Syte.Companion.syte == null) {
                throw IllegalStateException(
                    "Cannot fetch image bounds - you must initialize the SDK first by calling init()."
                )
            }

            syte!!.apply {
                callAnalytics("bounds")
                getImageBounds(imageUri, onComplete)
            }
        }

        /**
         * Retrieves the ads for a given offer.
         * It also makes a call to the analytics API to log the operation.
         *
         * @param offerUrl the URL of the offer whose ads are to be fetched.
         * @param onComplete a callback function to be invoked with the response.
         */
        fun getOffers(
            offerUrl: String,
            onComplete: (response: SyteResponse<OfferDetails>) -> Unit
        ) {
            if (syte == null) {
                throw IllegalStateException(
                    "Cannot get offer details - you must initialize the SDK first by calling init()."
                )
            }

            syte!!.apply {
                callAnalytics("offers")
                getOffers(offerUrl, onComplete)
            }
        }

        /**
         * Registers a callback function to be triggered when the Syte SDK is initialized and ready.
         * It will trigger immediately if the SDK was already initialized by the time this call was made.
         *
         * @param onSyteReady the callback to register to be called when the SDK
         * is initialized.
         */
        fun registerCallback(onSyteReady: () -> Unit) {
            if (syte != null && !isInitializing)
                onSyteReady()
            else
                onSyteReadyCallbacks.add(onSyteReady)
        }

        /**
         * Initializes the Syte singleton and fetches the current account configuration
         * from the remote API.
         *
         * @param appContext The app context needed to get the active network status
         * @param accountId The Syte developer account ID
         * @param token The Syte developer account token
         * @param client The Syte HTTP client through which API calls will be made
         * @param networkMonitor The network monitor in charge of fetching the active network status
         */
        private fun initializeSyte(
            appContext: Context, accountId: Int, token: String,
            syteHttpClient: SyteHttpClient, networkMonitor: NetworkMonitor
        ) {
            synchronized(this) {
                isInitializing = true
                val config = Config(accountId, token)
                val newSyte = Syte(appContext, config, syteHttpClient, networkMonitor)
                newSyte.loadRemoteConfig {
                    isInitializing = false
                    syte = newSyte
                    syte!!.callAnalytics("sdk_init")
                    notifyInitListeners()
                }
            }
        }

        /**
         * Notifies the registered listeners that the SDK has finished initializing.
         */
        internal fun notifyInitListeners() {
            onSyteReadyCallbacks.forEach { it() }
        }

        /**
         * Resets the state of the SDK.
         */
        internal fun reset() {
            syte = null
            isInitializing = false
            onSyteReadyCallbacks.clear()
            categoriesList = arrayOf()
        }
    }

    fun getOffers(
        offerUrl: String,
        onComplete: (response: SyteResponse<OfferDetails>) -> Unit
    ) {
        if (!mNetworkMonitor.isNetworkAvailable()) {
            logNetworkUnavailable("Cannot get offers")
            onComplete(noNetworkConnectionResponse())
            return
        }

        val category = mConfig.category
        val gender = mConfig.gender
        val currency = mConfig.currency
        val uri = Uri.parse(offerUrl).buildUpon()

        if (category.isNotEmpty())
            uri.appendQueryParameter("category", category)
        if (gender.isNotEmpty())
            uri.appendQueryParameter("gender", gender)
        if (currency.isNotEmpty())
            uri.appendQueryParameter("currency", currency)

        mHttpClient.getAdsForOffer(uri.toString(), onComplete)
    }

    private fun getImageBounds(
        imageUrl: String,
        onComplete: (response: SyteResponse<ImageBounds>) -> Unit
    ) {
        if (!mNetworkMonitor.isNetworkAvailable()) {
            logNetworkUnavailable("Cannot get image bounds")
            onComplete(noNetworkConnectionResponse())
            return
        }

        val accountId = mConfig.accountId
        val feed = mConfig.feed
        val token = mConfig.token
        val catalog = mConfig.catalog.joinToString()

        mHttpClient.getBoundsForImage(accountId, feed, token, catalog, imageUrl, onComplete)
    }

    @Throws(NotAnImageException::class)
    private fun getImageBounds(
        imageUri: Uri,
        onComplete: (response: SyteResponse<ImageBounds>) -> Unit
    ) {
        if (!mNetworkMonitor.isNetworkAvailable()) {
            logNetworkUnavailable("Cannot get image bounds")
            onComplete(noNetworkConnectionResponse())
            return
        }

        val accountId = mConfig.accountId
        val feed = mConfig.feed
        val token = mConfig.token
        val catalog = mConfig.catalog.joinToString()

        val instream = mContext!!.contentResolver.openInputStream(imageUri)
        val buf = ByteArray(instream!!.available())
        while (instream.read(buf) != -1);

        // Check if the URI points to a valid image file
        val bitmap =
            BitmapFactory.decodeByteArray(buf, 0, buf.size) ?: throw NotAnImageException(
                imageUri.toString()
            )

        val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), buf)

        mHttpClient.getBoundsForImageURI(accountId, feed, token, catalog, requestBody, onComplete)
    }

    /**
     * Initiates the call to the remote API to load the current account configuration
     * if a network connection is available, and triggers the [passed-in callback][onComplete] once
     * that's over, regardless of whether it succeeded or not.
     */
    private fun loadRemoteConfig(onComplete: () -> Unit = {}) {
        if (!mNetworkMonitor.isNetworkAvailable()) {
            logNetworkUnavailable("Cannot load remote account config")
            onComplete()
        } else {
            mHttpClient.loadRemoteConfig(mConfig.accountId) {
                val data = it.data
                if (data != null) {
                    mConfig.apply {
                        this.features = data.features
                        this.feed = data.feed
                    }
                }

                mHttpClient.loadCategories {
                    val data = it.data
                    if (data != null)
                        Syte.Companion.categoriesList = data
                    onComplete()
                }
            }
        }
    }

    /**
     * Calls the remote analytics API to log a call to one of the SDK's methods.
     *
     * @param name the name of the endpoint to which this analytics call corresponds.
     */
    private fun callAnalytics(name: String) {
        if (!mNetworkMonitor.isNetworkAvailable()) {
            logNetworkUnavailable("Cannot contact the analytics API")
            return
        }

        val url = Uri.parse("https://syteapi.com/et")
            .buildUpon()
            .appendQueryParameter("name", name)
            .appendQueryParameter("account_id", "${mConfig.accountId}")
            .appendQueryParameter("session_id", mConfig.sessionId)
            .appendQueryParameter("sig", mConfig.token)
            .appendQueryParameter("count", "1")
            .appendQueryParameter("tags", "android_sdk")
            .build()
            .toString()

        mHttpClient.callAnalytics(url)
    }

    /**
     * Holds the configuration details of the SDK.
     *
     * @property accountId The Syte developer account ID
     * @property token The Syte developer account token
     */
    class Config(
        var accountId: Int = -1,
        var token: String = "",
        var features: Array<String> = arrayOf(),
        var catalog: Array<String> = arrayOf(),
        var currency: String = "",
        var gender: String = "",
        var category: String = "",
        var feed: String = "",
        var sessionId: String = generateSessionId(40)
    ) {
        companion object {

            private val allowedCatalog = arrayOf("home", "general", "fashion")
            private val allowedGenders = arrayOf("male", "female", "boy", "girl", "hd", "general")

            fun verifyCatalog(catalog: Array<String>) {
                val invalidEntries = catalog.filter { it !in allowedCatalog }
                if (invalidEntries.isNotEmpty())
                    throw InvalidCatalogException(invalidEntries)
            }

            fun verifyCurrency(currency: String) {
                if (currency.length != 3)
                    throw InvalidCurrencyException(currency)
            }

            fun verifyGender(gender: String) {
                if (gender !in allowedGenders)
                    throw InvalidGenderException(gender)
            }

            fun verifyCategory(category: String) {
                if (category !in categoriesList)
                    throw InvalidCategoryException(category)
            }
        }
    }
}