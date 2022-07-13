package com.dactyl.restaurants.data

import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.model.RestaurantDTO
import javax.inject.Inject

class RestaurantMapper @Inject constructor() : Mapper<RestaurantDTO, Restaurant> {
	override fun map(from: RestaurantDTO) =
		Restaurant(
			name = from.name,
			location = from.location,
			userRating = from.userRating,
			cuisines = from.cuisines,
			photos = from.photos
		)
}
