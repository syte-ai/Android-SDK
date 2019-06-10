package com.syte.ai.data

/**
 * Created by Syte on 4/7/2019.
 */
data class AccountConfig(
    var accountId: Int = -1,
    var features: Array<String> = arrayOf(),
    var feed: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountConfig

        if (accountId != other.accountId) return false
        if (!features.contentEquals(other.features)) return false
        if (feed != other.feed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = accountId
        result = 31 * result + features.contentHashCode()
        result = 31 * result + feed.hashCode()
        return result
    }

}