package com.syte.ai.exceptions

/**
 * Created by Syte on 4/8/2019.
 */
class InvalidCatalogException(invalidEntries: List<String>) : Exception(
    if (invalidEntries.size == 1)
        "An invalid catalog entry was provided: ${invalidEntries[0]}"
    else
        "Multiple invalid catalog entries were provided: $invalidEntries"
)