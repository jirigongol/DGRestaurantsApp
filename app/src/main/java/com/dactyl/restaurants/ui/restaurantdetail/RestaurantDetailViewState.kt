package com.dactyl.restaurants.ui.restaurantdetail

import com.dactyl.restaurants.model.Restaurant

data class RestaurantDetailViewState(
	val restaurant: Restaurant? = null,
	val loading: Boolean = false,
	val error: String? = null,
)
