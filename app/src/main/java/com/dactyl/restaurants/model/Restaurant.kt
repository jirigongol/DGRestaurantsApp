package com.dactyl.restaurants.model

import com.squareup.moshi.Json

data class RestaurantsDTO(
	@Json(name = "restaurants")
	val restaurants: List<RestaurantXDTO>,
	@Json(name = "results_found")
	val results_found: Int,
	@Json(name = "results_shown")
	val results_shown: Int,
	@Json(name = "results_start")
	val results_start: Int
)

data class Restaurants(
	val restaurants: List<RestaurantXDTO>,
	val results_found: Int,
	val results_shown: Int,
	val results_start: Int
)

data class RestaurantXDTO(
	@Json(name = "restaurant")
	val restaurant: RestaurantDTO
)

data class RestaurantDTO(
	@Json(name = "name")
	val name: String,
	@Json(name = "location")
	val location: LocationDTO,
	@Json(name = "photos")
	val photos: List<PhotosXDTO>

)

data class LocationDTO(
	@Json(name = "address")
	val address: String,
	@Json(name = "city")
	val city: String,
	@Json(name = "latitude")
	val latitude: String,
	@Json(name = "longitude")
	val longitude: String,
)

data class PhotosXDTO(
	@Json(name = "url")
	val url: String,
)
