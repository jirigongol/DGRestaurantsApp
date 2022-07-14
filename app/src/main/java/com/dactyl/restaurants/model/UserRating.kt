package com.dactyl.restaurants.model

import com.squareup.moshi.Json

class UserRatingDTO(
	@Json(name = "aggregate_rating") val rating: String,
	@Json(name = "rating_color") val color: String
)

data class UserRating(
	val rating: String,
	val color: String
)
