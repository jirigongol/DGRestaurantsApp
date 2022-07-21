package com.dactyl.restaurants.ui.restaurantsmap

import com.dactyl.restaurants.model.Restaurant
import com.google.android.gms.maps.model.LatLng

data class RestaurantsMapViewState(
	val restaurants: List<Restaurant> = emptyList(),
	val myPosition: LatLng = LatLng(.0, .0),
	val loading: Boolean = false,
	val error: String? = null
)
