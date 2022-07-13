package com.dactyl.restaurants.network

import com.dactyl.restaurants.extensions.Either
import com.dactyl.restaurants.model.Restaurants
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
	private val api: RestaurantApi
) {
	suspend fun getRestaurants(): Either<String, List<Restaurants>> {
		
	}

}
