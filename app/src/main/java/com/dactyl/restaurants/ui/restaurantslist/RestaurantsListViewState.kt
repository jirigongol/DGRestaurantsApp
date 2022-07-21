package com.dactyl.restaurants.ui.restaurantslist

import com.dactyl.restaurants.model.Location
import com.dactyl.restaurants.model.Restaurant
import com.google.android.gms.maps.model.LatLng

data class RestaurantsListViewState(
	val restaurants: List<Restaurant> = emptyList(),
	val location: LatLng? = LatLng(0.0,0.0),
	val loading: Boolean = false,
	val error: String? = null
)
