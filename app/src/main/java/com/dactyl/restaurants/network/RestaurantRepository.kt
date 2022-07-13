package com.dactyl.restaurants.network

import com.dactyl.restaurants.data.RestaurantMapper
import com.dactyl.restaurants.extensions.Either
import com.dactyl.restaurants.model.Restaurants
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
	private val api: RestaurantApi,
	private val restaurantMapper: RestaurantMapper,
) {
	suspend fun getRestaurants(): Either<String, Restaurants> {
		return try {
			val restaurants = api.getRestaurants()
			Either.Value( restaurantMapper.map(restaurants))
		} catch (exception: Throwable) {
			Either.Error(exception.localizedMessage?: "Network error")
		}
	}

}
