/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.scout48.auto.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

/**
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class CarProperty (
        val id: Int,
        val make: String?,
        val model: String?,
        val price: Int? = 0,
        val firstRegistration: String?,
        val mileage: String?,
        val fuel: String?,

        // used to map img_src from the JSON to imgSrcUrl in our class
        @Json(name = "images") val imgSrcUrls: List<CarImageUrl>?,

        val description: String?,
        val seller: Seller?,
)

    : Parcelable {
        // TODO: show a green overlay icon on the car picture in case of electric cars.
        val isElectric
        get() = fuel != "Gasoline" && fuel != "Diesel"
}

@Parcelize
data class CarImageUrl(@Json(name = "url") val imgSrcUrl: String) : Parcelable

@Parcelize
data class Seller(
        val type: String,
        val phone: String,
        val city: String
) : Parcelable
