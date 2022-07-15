package com.dactyl.restaurants.ui.restaurantslist

import com.dactyl.restaurants.model.Restaurant

data class RestaurantsListViewState(
	val restaurants: List<Restaurant> = emptyList(),
	val loading: Boolean = false,
	val error: String? = null
)
