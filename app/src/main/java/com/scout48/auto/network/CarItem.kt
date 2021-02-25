package com.scout48.auto.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class CarItem(
        val id: Int,
        val carSellerData: CarProperty,
        val carBuyerNote: String?
) : Parcelable