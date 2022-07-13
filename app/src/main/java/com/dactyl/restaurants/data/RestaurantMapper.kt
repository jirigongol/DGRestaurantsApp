package com.dactyl.restaurants.data

import com.dactyl.restaurants.model.Restaurants
import com.dactyl.restaurants.model.RestaurantsDTO
import javax.inject.Inject

class RestaurantMapper @Inject constructor() : Mapper<RestaurantsDTO, Restaurants> {
	override fun map(from: RestaurantsDTO) =
		Restaurants(
			restaurants = from.restaurants,
			results_found = from.results_found,
			results_shown = from.results_shown,
			results_start = from.results_start
		)
}
