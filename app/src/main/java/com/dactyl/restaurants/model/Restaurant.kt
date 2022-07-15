package com.dactyl.restaurants.model

import com.dactyl.restaurants.data.entity.RestaurantEntity
import com.squareup.moshi.Json

data class Restaurant(
	val id: String,
	val name: String,
	val location: Location,
	val userRating: UserRating,
	val cuisines: String,
	val photos: List<Photo>
)

data class RestaurantDTO(
	@Json(name = "id") val id: String,
	@Json(name = "name") val name: String,
	@Json(name = "location") val location: LocationDTO,
	@Json(name = "user_rating") val userRating: UserRatingDTO,
	@Json(name = "cuisines") val cuisines: String,
	@Json(name = "photos") val photos: List<PhotoDTOWrapper>,
)

fun RestaurantDTO.toEntity() = RestaurantEntity(
	id = id,
	name = name,
	cuisines = cuisines,
	aggregateRating = userRating.rating,
	ratingColor = userRating.color,
	address = location.address,
	latitude = location.latitude,
	longitude = location.longitude,
)

