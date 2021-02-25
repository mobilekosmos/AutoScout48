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
import kotlinx.parcelize.Parcelize

/**
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class CarBuyerNote (
        val vehicleId: Int,
        val note: String
) : Parcelable