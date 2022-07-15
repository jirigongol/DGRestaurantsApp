package com.dactyl.restaurants.ui.restaurantdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.network.RestaurantRepository
import com.dactyl.restaurants.ui.restaurantslist.RestaurantsListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantDetailViewModel @Inject constructor(
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
		observeRestaurantDetail()
	}

	private fun observeRestaurantDetail() {
		viewModelScope.launch {
			restaurantRepository.observeRestaurantDetail()
		}
	}

}
