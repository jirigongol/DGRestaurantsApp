package com.dactyl.restaurants.model

import com.squareup.moshi.Json

data class LocationDTO(
	@Json(name = "address") val address: String,
	@Json(name = "latitude") val latitude: String,
	@Json(name = "longitude") val longitude: String
)

data class Location(
	val address: String,
	val latitude: String,
	val longitude: String
)
