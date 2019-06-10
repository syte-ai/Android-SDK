package com.syte.ai.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Syte on 4/8/2019.
 */
data class ImageBounds(var bounds: Array<Bound> = arrayOf()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageBounds

        if (!bounds.contentEquals(other.bounds)) return false

        return true
    }

    override fun hashCode(): Int {
        return bounds.contentHashCode()
    }
}

data class Bound(
    @SerializedName("offers") var offerUrl: String = "",
    var label: String = "",
    var gender: String = "",
    var center: Array<Double> = arrayOf(),
    var catalog: String = "",
    var b1: Array<Double> = arrayOf(),
    var b0: Array<Double> = arrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bound

        if (offerUrl != other.offerUrl) return false
        if (label != other.label) return false
        if (gender != other.gender) return false
        if (!center.contentEquals(other.center)) return false
        if (catalog != other.catalog) return false
        if (!b1.contentEquals(other.b1)) return false
        if (!b0.contentEquals(other.b0)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = offerUrl.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + center.contentHashCode()
        result = 31 * result + catalog.hashCode()
        result = 31 * result + b1.contentHashCode()
        result = 31 * result + b0.contentHashCode()
        return result
    }
}