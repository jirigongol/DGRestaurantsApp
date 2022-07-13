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

data class RestaurantXDTO(
	@Json(name = "restaurant")
	val restaurant: RestaurantDTO
)

data class RestaurantDTO(
	@Json(name = "name")
	val name: String,
	@Json(name = "location")
	val location: Location,
	@Json(name = "user_rating")
	val userRating: UserRating,
	@Json(name = "cuisines")
	val cuisines: String,
	@Json(name = "photos")
	val photos: List<PhotosX>,

	)

data class Restaurant(
	val name: String,
	val location: Location,
	val userRating: UserRating,
	val cuisines: String,
	val photos: List<PhotosX>
)

data class Location(
	val address: String,
	val city: String,
	val latitude: String,
	val longitude: String
)

data class UserRating(
	val aggregate_rating: String,
	val rating_color: String,
	val rating_text: String,
	val votes: Int
)

data class PhotosX(
	val photo: Photo
)

data class Photo(
	val url: String
)

