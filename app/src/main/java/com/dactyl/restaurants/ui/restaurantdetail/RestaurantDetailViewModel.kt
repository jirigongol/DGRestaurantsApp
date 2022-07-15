package com.dactyl.restaurants.ui.restaurantdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.network.RestaurantRepository
import com.dactyl.restaurants.ui.navigation.RestaurantsNavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val restaurantRepository: RestaurantRepository
) : ViewModel() {

	private val id =
		requireNotNull(savedStateHandle.get<String>(RestaurantsNavArguments.RESTAURANT_ID_KEY)) {
			"Restaurant id is missing..."
		}

	private val _viewState = MutableStateFlow(RestaurantDetailViewState(loading = true))
	private val _restaurantDetail = MutableStateFlow<Restaurant?>(null)

	val viewState = combine(_viewState, _restaurantDetail) { state, detail ->
		when {
			detail != null -> RestaurantDetailViewState(restaurant = detail)
			else -> state
		}
	}

	init {
		observeRestaurantDetail()
	}

	private fun observeRestaurantDetail() {
		viewModelScope.launch {
			restaurantRepository.observeRestaurantDetail(id).collect { detail ->
				_restaurantDetail.value = detail
			}
		}
	}
}
