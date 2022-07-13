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
	val location: LocationDTO,
	@Json(name = "user_rating")
	val userRating: UserRatingDTO,
	@Json(name = "cuisines")
	val cuisines: String,
	@Json(name = "photos")
	val photos: List<PhotosXDTO>,

	)

data class Restaurant(
	val name: String,
	val location: LocationDTO,
	val userRating: UserRatingDTO,
	val cuisines: String,
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

data class UserRatingDTO(
	@Json(name = "aggregate_rating")
	val aggregate_rating: String,
	@Json(name = "rating_color")
	val rating_color: String,
	@Json(name = "rating_text")
	val rating_text: String,
	@Json(name = "votes")
	val votes: Int
)

data class PhotosXDTO(
	@Json(name = "photo")
	val photo: PhotoDTO
)

data class PhotoDTO(
	@Json(name = "url")
	val url: String
)

