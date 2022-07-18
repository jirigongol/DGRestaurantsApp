package com.dactyl.restaurants.ui.restaurantsmap

import com.dactyl.restaurants.model.Restaurant

data class RestaurantsMapViewState(
	val restaurants: List<Restaurant> = emptyList(),
	val loading: Boolean = false,
	val error: String? = null
)
