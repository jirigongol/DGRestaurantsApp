package com.dactyl.restaurants.network

import com.dactyl.restaurants.data.dao.RestaurantsDao
import com.dactyl.restaurants.data.entity.RestaurantEntity
import com.dactyl.restaurants.data.entity.toDomain
import com.dactyl.restaurants.extensions.Either
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
	private val api: RestaurantApi,
	private val restaurantsDao: RestaurantsDao
) {
	suspend fun getRestaurants(): Either<String, Int> {
		return try {
			val restaurants = api.getRestaurants()

			restaurantsDao.insertRestaurant(restaurants.restaurants.map { it.restaurant.toEntity() })

			Either.Value(restaurants.restaurants.size)
		} catch (exception: Throwable) {
			Either.Error(exception.localizedMessage ?: "Network error")
		}
	}

	fun observeRestaurants(): Flow<List<Restaurant>> =
		restaurantsDao.observeMovieDetail().map { it.map(RestaurantEntity::toDomain)}
}
