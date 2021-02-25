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

package com.scout48.auto.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scout48.auto.data.CarBuyerNote
import com.scout48.auto.data.CarItem
import com.scout48.auto.data.CarProperty
import com.scout48.auto.network.*
import kotlinx.coroutines.launch

enum class CarsApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val mStatus = MutableLiveData<CarsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<CarsApiStatus>
        get() = mStatus

    // Internally, we use a MutableLiveData, because we will be updating the List of CarItem
    // with new values
    private val mCars = MutableLiveData<List<CarItem>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val cars: LiveData<List<CarItem>>
        get() = mCars

    // LiveData to handle navigation to the selected property
    private val _navigateToSelectedItem = MutableLiveData<CarProperty>()
    val navigateToSelectedItem: LiveData<CarProperty>
        get() = _navigateToSelectedItem

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getCars(CarsApiFilter.SHOW_ALL)
    }

    /**
     * Gets filtered Mars real estate property information from the Mars API Retrofit service and
     * updates the [CarItem] [List] and [CarsApiStatus] [LiveData]. The Retrofit service
     * returns a coroutine Deferred, which we await to get the result of the transaction.
     * @param filter the [CarsApiFilter] that is sent as part of the web server request
     */
    private fun getCars(filter: CarsApiFilter) {
        viewModelScope.launch {
            mStatus.value = CarsApiStatus.LOADING
            try {
                val carsList = CarsApi.RETROFIT_SERVICE.getProperties()
                val carsNotesList = CarsApi.RETROFIT_SERVICE.getCarNotes()

                // Creates a new CarItem object with the values from the first API request and the notes from the second request, some cars have a note, not all.
                // Java-Style, not kotlin-style and probably not so efficient.
//                val carsArrayList: ArrayList<CarItem> = arrayListOf()
//                for (car in carsList) {
//                    var noteString = ""
//                    for (note in carsNotesList) {
//                        if (note.vehicleId == car.id) {
//                            noteString = note.note
//                        }
//                    }
//                    val carItem = CarItem(car.id, car, noteString)
//                    carsArrayList.add(carItem)
//                }
//                mCars.value = carsArrayList

                // Kotlin-style of above code in getCarItemList()
                mCars.value = getCarItemList(carsList, carsNotesList)

                mStatus.value = CarsApiStatus.DONE
            } catch (e: Exception) {
                mStatus.value = CarsApiStatus.ERROR
                Log.e("OverviewViewModel", e.toString())
                mCars.value = ArrayList()
            }
        }
    }

    // TODO: write UNIT Test
    private fun getCarItemList(carsList: List<CarProperty>, carsNotesList: List<CarBuyerNote>) =
            carsList.map { a -> CarItem(a.id, a, carsNotesList.find { a.id == it.vehicleId }?.note) }

    // TODO: link with UI.
    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * by calling [getCars]
     * @param filter the [CarsApiFilter] that is sent as part of the web server request
     */
    fun updateFilter(filter: CarsApiFilter) {
        getCars(filter)
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedItem] [MutableLiveData]
     * @param CarItem The [CarItem] that was clicked on.
     */
    fun displayCarDetails(carItem: CarItem) {
        _navigateToSelectedItem.value = carItem.carSellerData
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayCarDetailsComplete() {
        _navigateToSelectedItem.value = null
    }
}
