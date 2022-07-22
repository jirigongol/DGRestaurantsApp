package com.dactyl.restaurants.ui.restaurantslist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.extensions.fold
import com.dactyl.restaurants.location.LocationInteractor
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.network.RestaurantRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@HiltViewModel
class RestaurantsListViewModel @Inject constructor(
	private val locationInteractor: LocationInteractor,
	private val restaurantRepository: RestaurantRepository
) : ViewModel() {
	private val _viewState = MutableStateFlow(RestaurantsListViewState(loading = true))
	private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())

	var permissions = mutableStateOf(false)
	var permissionsDenied = mutableStateOf(true)

	private var cachedRestaurantList = listOf<Restaurant>().sortByDistance()
	private var isSearchStarting = true
	private var isSearching = mutableStateOf(false)

	private var _userCurrentLocation = MutableStateFlow(LatLng(0.0, 0.0))

	val viewState =
		combine(_viewState, _restaurants, _userCurrentLocation) { state, restaurants, location ->
			when {
				(restaurants.isNotEmpty()) || isSearching.value ->
					RestaurantsListViewState(
						restaurants = if (permissions.value) {
							restaurants.sortByDistance()
						} else {
							restaurants.sortByAlphabet()
						},location = location
					)
				else -> state
			}
		}

	init {
		observeRestaurants()

		viewModelScope.launch {
			fetchRestaurants()
		}
	}

	override fun onCleared() {
		locationInteractor.stop()
		super.onCleared()
	}

	private suspend fun fetchRestaurants() {
		restaurantRepository.getRestaurants().fold(
			{ error ->
				Log.d("TAG", "RestaurantsListLoadingError: $error")
				_viewState.update {
					RestaurantsListViewState(
						error = error
					)
				}
			},
			{ updatedRestaurantsCount ->
				Log.e("TAG", "RestaurantsListSuccess: $updatedRestaurantsCount")
			}
		)
	}

	private fun observeRestaurants() {
		viewModelScope.launch {
			restaurantRepository.observeRestaurants().collect {
				_restaurants.value = it
			}
		}
	}

	fun observeLocationData() {
		locationInteractor.start()
		viewModelScope.launch {
			locationInteractor.locationData.collectLatest {
				_userCurrentLocation.value = LatLng(it.latitude, it.longitude)

			}
		}
	}

	fun searchRestaurantsList(query: String) {
		val listToSearch = if (isSearchStarting) {
			_restaurants.value
		} else {
			cachedRestaurantList
		}
		viewModelScope.launch(Dispatchers.Default) {
			if (query.isEmpty()) {
				_restaurants.value = cachedRestaurantList
				isSearching.value = false
				isSearchStarting = true
				return@launch
			}
			val results = listToSearch.filter {
				it.name.contains(query.trim(), ignoreCase = true)
			}.sortByDistance()


			if (isSearchStarting) {
				cachedRestaurantList = _restaurants.value
				isSearchStarting = false
			}
			_restaurants.value = results
			isSearching.value = true
		}
	}

	private fun List<Restaurant>.sortByDistance(): List<Restaurant> {
		val result = this.sortedBy {
			distanceInKm(
				_userCurrentLocation.value.latitude,
				_userCurrentLocation.value.longitude,
				it.location.latitude.toDouble(),
				it.location.longitude.toDouble()
			)
		}
		return result
	}

	private fun List<Restaurant>.sortByAlphabet(): List<Restaurant> {
		return sortedBy { restaurant -> restaurant.name }
	}

	private fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
		val theta = lon1 - lon2
		var dist =
			sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(
				deg2rad(lat2)
			) * cos(deg2rad(theta))
		dist = acos(dist)
		dist = rad2deg(dist)
		dist *= 60 * 1.1515
		dist *= 1.609344
		return dist
	}

	private fun deg2rad(deg: Double): Double {
		return deg * Math.PI / 180.0
	}

	private fun rad2deg(rad: Double): Double {
		return rad * 180.0 / Math.PI
	}
}

