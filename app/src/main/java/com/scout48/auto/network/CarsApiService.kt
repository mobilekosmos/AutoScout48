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

import com.scout48.auto.data.CarBuyerNote
import com.scout48.auto.data.CarProperty
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class CarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all") }

private const val BASE_URL = "https://private-fe87c-simpleclassifieds.apiary-mock.com/"
private const val BASE_URL_NOTES = "https://private-e7c3d8-classifiednotes.apiary-mock.com/"


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("http://localhost/")
        .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface CarsApiService {
    /**
     * Returns a Coroutine [List] of [CarProperty] which can be fetched in a Coroutine scope.
     * The @GET annotation indicates that the endpoint will be requested with the GET
     * HTTP method. In this case we don't use an endpoint, we only use the base URL, so no parameter
     * is passed to GET.
     */
    @GET(" ")
    suspend fun getProperties(@Query("filter") type: String): List<CarProperty>

    @GET(BASE_URL)
    suspend fun getProperties(): List<CarProperty>

    @GET(BASE_URL_NOTES)
    suspend fun getCarNotes(): List<CarBuyerNote>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object CarsApi {
    val RETROFIT_SERVICE : CarsApiService by lazy { retrofit.create(CarsApiService::class.java) }
}
