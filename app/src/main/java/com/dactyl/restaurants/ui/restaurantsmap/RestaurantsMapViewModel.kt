package com.dactyl.restaurants.ui.restaurantsmap

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.location.LocationInteractor
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.network.RestaurantRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsMapViewModel @Inject constructor(
	private val locationInteractor: LocationInteractor,
	private val restaurantRepository: RestaurantRepository
) : ViewModel() {

	private val _viewState = MutableStateFlow(RestaurantsMapViewState(loading = true))
	private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())

//	var _userCurrentLocation = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))

//	private var _userCurrentLat = mutableStateOf(0.0)
//	var userCurrentLat: MutableState<Double> = _userCurrentLat
//
//	private var _userCurrentLng = mutableStateOf(0.0)
//	var userCurrentLng: MutableState<Double> = _userCurrentLng

	private val _currentPosition = MutableStateFlow<LatLng>(LatLng(.0, .0))
//	val currentPosition = _currentPosition.asStateFlow()

	val viewState = combine(_viewState, _restaurants, _currentPosition) { state, restaurants, myPosition ->
		when {
			restaurants.isNotEmpty() -> RestaurantsMapViewState(restaurants = restaurants, myPosition = myPosition)
			else -> state
		}
	}

	init {
		Log.d("xxxx", "INIT RestaurantsMapViewModel")

		observeRestaurants()
		locationInteractor.start()

		viewModelScope.launch {
			locationInteractor.locationData.collectLatest {
				Log.d("xxxx", "Maps VM location: $it")
				_currentPosition.value =  LatLng(it.latitude, it.longitude)
			}
		}
	}

	override fun onCleared() {
		Log.d("xxxx", "onCleared MAP: ")
		locationInteractor.stop()
		super.onCleared()
	}


	private fun observeRestaurants() {
		viewModelScope.launch {
			restaurantRepository.observeRestaurants().collect {
				Log.d("TAG", "observeRestaurants: ${it.size}")
				_restaurants.value = it
			}
		}
	}

//	fun saveLocation(lat: Double, lon: Double) {
//		_userCurrentLocation.value = LatLng(lat, lon)
//	}

}
