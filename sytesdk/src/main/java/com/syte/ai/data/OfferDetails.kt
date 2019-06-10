package com.syte.ai.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Syte on 4/9/2019.
 */
class OfferDetails(var ads: Array<Ad> = arrayOf())

class Ad {

    @SerializedName("bbCategories")
    @Expose
    var bbCategories: List<String>? = null
    @SerializedName("brand")
    @Expose
    var brand: String? = null
    @SerializedName("categories")
    @Expose
    var categories: List<String>? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("floatOriginalPrice")
    @Expose
    var floatOriginalPrice: Double? = null
    @SerializedName("floatPrice")
    @Expose
    var floatPrice: Double? = null
    @SerializedName("imageUrl")
    @Expose
    var imageUrl: String? = null
    @SerializedName("merchant")
    @Expose
    var merchant: String? = null
    @SerializedName("offer")
    @Expose
    var offer: String? = null
    @SerializedName("originalPrice")
    @Expose
    var originalPrice: String? = null
    @SerializedName("original_data")
    @Expose
    var originalData: OriginalData? = null
    @SerializedName("price")
    @Expose
    var price: String? = null
    @SerializedName("sku")
    @Expose
    var sku: String? = null
}

class OriginalData {

    @SerializedName("Fashion:suitable_for")
    @Expose
    var fashionSuitableFor: String? = null
    @SerializedName("alternate_image")
    @Expose
    var alternateImage: String? = null
    @SerializedName("aw_deep_link")
    @Expose
    var awDeepLink: String? = null
    @SerializedName("aw_image_url")
    @Expose
    var awImageUrl: String? = null
    @SerializedName("aw_product_id")
    @Expose
    var awProductId: String? = null
    @SerializedName("aw_thumb_url")
    @Expose
    var awThumbUrl: String? = null
    @SerializedName("base_price")
    @Expose
    var basePrice: String? = null
    @SerializedName("brand_id")
    @Expose
    var brandId: String? = null
    @SerializedName("brand_name")
    @Expose
    var brandName: String? = null
    @SerializedName("category_id")
    @Expose
    var categoryId: String? = null
    @SerializedName("category_name")
    @Expose
    var categoryName: String? = null
    @SerializedName("currency")
    @Expose
    var currency: String? = null
    @SerializedName("custom_1")
    @Expose
    var custom1: String? = null
    @SerializedName("custom_2")
    @Expose
    var custom2: String? = null
    @SerializedName("data_feed_id")
    @Expose
    var dataFeedId: String? = null
    @SerializedName("delivery_cost")
    @Expose
    var deliveryCost: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("display_price")
    @Expose
    var displayPrice: String? = null
    @SerializedName("language")
    @Expose
    var language: String? = null
    @SerializedName("merchant_category")
    @Expose
    var merchantCategory: String? = null
    @SerializedName("merchant_deep_link")
    @Expose
    var merchantDeepLink: String? = null
    @SerializedName("merchant_id")
    @Expose
    var merchantId: String? = null
    @SerializedName("merchant_image_url")
    @Expose
    var merchantImageUrl: String? = null
    @SerializedName("merchant_name")
    @Expose
    var merchantName: String? = null
    @SerializedName("merchant_product_id")
    @Expose
    var merchantProductId: String? = null
    @SerializedName("product_name")
    @Expose
    var productName: String? = null
    @SerializedName("rrp_price")
    @Expose
    var rrpPrice: String? = null
    @SerializedName("search_price")
    @Expose
    var searchPrice: String? = null
    @SerializedName("store_price")
    @Expose
    var storePrice: String? = null

}