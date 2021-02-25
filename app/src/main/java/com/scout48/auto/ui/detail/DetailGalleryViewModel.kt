/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.scout48.auto.ui.detail

import android.app.Application
import androidx.lifecycle.*
import com.scout48.auto.data.CarProperty

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [CarProperty].
 */
class DetailGalleryViewModel(carProperty: CarProperty,
                             app: Application) : AndroidViewModel(app) {

    // The internal MutableLiveData for the selected property
    private val _selectedProperty = MutableLiveData<CarProperty>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<CarProperty>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = carProperty
    }
}

