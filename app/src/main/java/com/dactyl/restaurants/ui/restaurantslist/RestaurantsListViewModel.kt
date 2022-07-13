package com.dactyl.restaurants.ui.restaurantslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dactyl.restaurants.extensions.fold
import com.dactyl.restaurants.network.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsListViewModel @Inject constructor(
	private val restaurantRepository: RestaurantRepository
): ViewModel() {

	private val _viewState = MutableStateFlow(RestaurantsListViewState(loading = true))
	val viewState = _viewState.asStateFlow()

	init {
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
			{ restaurantsList ->
				Log.e("TAG", "RestaurantsListSuccess: ${restaurantsList.size}")
				_viewState.update {
					RestaurantsListViewState(
						restaurants = restaurantsList
					)
				}
			}
		)
	}

}
