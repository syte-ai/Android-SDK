package com.syte.ai.network

/**
 * Created by Syte on 4/8/2019.
 *
 * This file holds different custom Gson deserializers to be used to deserialize
 * incoming HTTP responses.
 */
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.syte.ai.data.AccountConfig
import com.syte.ai.data.ImageBounds
import java.lang.reflect.Type

/**
 * A custom Gson deserializer for incoming [account configuration responses][AccountConfig]
 * from the [HttpClient].
 */
class AccountConfigResponseDeserializer : JsonDeserializer<com.syte.ai.data.AccountConfig> {
    override fun deserialize(
        json: JsonElement?, typeOfT: Type?,
        context: JsonDeserializationContext?
    ): com.syte.ai.data.AccountConfig {
        val dataRoot = json?.asJsonObject?.get("data")?.asJsonObject
        val syteAppRoot = dataRoot?.get("products")?.asJsonObject?.get("syteapp")?.asJsonObject
        val featuresRoot = syteAppRoot?.get("features")?.asJsonObject

        return com.syte.ai.data.AccountConfig(
            accountId = dataRoot?.get("accountId")?.asInt ?: -1,
            features = featuresRoot?.keySet()?.toTypedArray() ?: arrayOf(),
            feed = syteAppRoot?.get("feed")?.asString ?: ""
        )
    }
}

/**
 * A custom Gson deserializer for incoming [image bound responses][ImageBounds]
 * from the [HttpClient].
 */
class ImageBoundsResponseDeserializer : JsonDeserializer<ImageBounds> {
    override fun deserialize(
        json: JsonElement?, typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ImageBounds {
        val root = json?.asJsonObject
        val keySet = root?.keySet()
        if (keySet != null && keySet.isNotEmpty()) {
            val key = keySet.elementAt(0)
            val boundsRoot = root.get(key).asJsonArray
            val boundsList = arrayListOf<com.syte.ai.data.Bound>()

            boundsRoot.forEach {
                val jsonBound = it.asJsonObject
                val jsonCenter = jsonBound.get("center").asJsonArray
                val jsonB1 = jsonBound.get("b1").asJsonArray
                val jsonB0 = jsonBound.get("b0").asJsonArray

                boundsList.add(
                    com.syte.ai.data.Bound(
                        offerUrl = jsonBound.get("offers").asString,
                        label = jsonBound.get("label").asString,
                        gender = jsonBound.get("gender").asString,
                        center = arrayOf(
                            jsonCenter.get(0).asDouble,
                            jsonCenter.get(1).asDouble
                        ),
                        catalog = jsonBound.get("catalog")?.asString ?: "",
                        b1 = arrayOf(
                            jsonB1.get(0).asDouble,
                            jsonB1.get(1).asDouble
                        ),
                        b0 = arrayOf(
                            jsonB0.get(0).asDouble,
                            jsonB0.get(1).asDouble
                        )
                    )
                )
            }

            return ImageBounds(boundsList.toTypedArray())
        }

        return ImageBounds()
    }
}

/**
 * A custom Gson deserializer for incoming category lists from the [HttpClient].
 */
class CategoriesDeserializer : JsonDeserializer<Array<String>> {
    override fun deserialize(
        json: JsonElement?, typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Array<String> {
        val catsRoot = json?.asJsonObject?.get("force_cats")?.asJsonArray
        return Gson().fromJson(catsRoot, Array<String>::class.java)
    }
}