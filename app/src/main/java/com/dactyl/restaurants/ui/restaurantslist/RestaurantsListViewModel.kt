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

@HiltViewModel
class RestaurantsListViewModel @Inject constructor(
	private val locationInteractor: LocationInteractor,
	private val restaurantRepository: RestaurantRepository
) : ViewModel() {
	private val _viewState = MutableStateFlow(RestaurantsListViewState(loading = true))
	private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())

	private var cachedRestaurantList = listOf<Restaurant>().sortByDistance()
	private var isSearchStarting = true
	var isSearching = mutableStateOf(false)

	private var _userCurrentLocation = MutableStateFlow<LatLng>(LatLng(0.0, 0.0))

	val viewState =
		combine(_viewState, _restaurants, _userCurrentLocation) { state, restaurants, location ->
			when {
				restaurants.isNotEmpty() || isSearching.value -> RestaurantsListViewState(
					restaurants = restaurants.sortByDistance(),
					location = location
				)
				else -> state
			}
		}

	init {
		Log.d("xxxx", "INIT RestaurantsListViewModel")
		observeRestaurants()

		viewModelScope.launch {
			fetchRestaurants()
		}

		observeLocationData()
		locationInteractor.start()
	}

	override fun onCleared() {
		Log.d("xxxx", "onCleared LIST: ")
		locationInteractor.stop()
		super.onCleared()
	}

	private suspend fun fetchRestaurants() {
		Log.e("TAG", "RestaurantsList - Start fetching data.")
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
				Log.d("TAG", "observeRestaurants: ${it.size}")
				_restaurants.value = it
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

	private fun observeLocationData() {
		viewModelScope.launch {
			locationInteractor.locationData.collectLatest {
				Log.d("xxxx", "List VM location: $it")
				_userCurrentLocation.value =  LatLng(it.latitude, it.longitude)

			}
		}
	}
}

