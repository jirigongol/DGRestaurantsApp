package com.dactyl.restaurants.ui.restaurantsmap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.location.LocationInteractor
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.network.RestaurantRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsMapViewModel @Inject constructor(
	private val locationInteractor: LocationInteractor,
	private val restaurantRepository: RestaurantRepository,
	@ApplicationContext private val context: Context
) : ViewModel() {

	private val _viewState = MutableStateFlow(RestaurantsMapViewState(loading = true))
	private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())

	private val _userCurrentLocation = MutableStateFlow(LatLng(.0, .0))

	val viewState =
		combine(_viewState, _restaurants, _userCurrentLocation) { state, restaurants, myPosition ->
			when {
				restaurants.isNotEmpty() -> RestaurantsMapViewState(
					restaurants = restaurants,
					myPosition = myPosition
				)
				else -> state
			}
		}

	init {
		observeRestaurants()

		if (!hasPermissions()) {
			locationInteractor.start()
			observeLocationData()
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
				_restaurants.value = it
			}
		}
	}

	private fun hasPermissions() =
		(ActivityCompat.checkSelfPermission(
			context,
			Manifest.permission.ACCESS_FINE_LOCATION
		) != PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(
				context,
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED)

	private fun observeLocationData() {
		locationInteractor.start()
		viewModelScope.launch {
			locationInteractor.locationData.collectLatest {
				_userCurrentLocation.value = LatLng(it.latitude, it.longitude)

			}
		}
	}
}
