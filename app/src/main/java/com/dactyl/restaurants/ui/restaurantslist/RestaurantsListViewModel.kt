package com.dactyl.restaurants.ui.restaurantslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.extensions.fold
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.network.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsListViewModel @Inject constructor(
	private val restaurantRepository: RestaurantRepository
) : ViewModel() {

	private val _viewState = MutableStateFlow(RestaurantsListViewState(loading = true))
	private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())

	val viewState = combine(_viewState, _restaurants) { state, restaurants ->
		when {
			restaurants.isNotEmpty() -> RestaurantsListViewState(restaurants = restaurants)
			else -> state
		}
	}

	init {
		observeRestaurants()

		viewModelScope.launch {
			fetchRestaurants()
		}
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
				Log.e("TAG", "MovieListSuccess: $updatedRestaurantsCount")
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

}
