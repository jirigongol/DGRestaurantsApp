package com.dactyl.restaurants.network

import androidx.room.withTransaction
import com.dactyl.restaurants.data.dao.RestaurantsDao
import com.dactyl.restaurants.data.entity.RestaurantWithPhotos
import com.dactyl.restaurants.data.entity.toDomain
import com.dactyl.restaurants.database.RestaurantsDatabase
import com.dactyl.restaurants.extensions.Either
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
	private val api: RestaurantApi,
	private val restaurantsDao: RestaurantsDao,
	private val database: RestaurantsDatabase
) {
	suspend fun getRestaurants(): Either<String, Int> {
		return try {
			val response = api.getRestaurants()

			database.withTransaction {
				response.restaurants.map { restaurantWrapper ->
					// Store restaurants
					restaurantsDao.insertRestaurant(restaurantWrapper.restaurant.toEntity())

					// Store photos
					restaurantsDao.insertPhotos(
						restaurantWrapper.restaurant.photos.map { it.photos.toEntity(restaurantId = restaurantWrapper.restaurant.id) }
					)
				}
			}

			Either.Value(response.restaurants.size)
		} catch (exception: Throwable) {
			Either.Error(exception.localizedMessage ?: "Network error")
		}
	}

	fun observeRestaurants(): Flow<List<Restaurant>> =
		restaurantsDao.observeRestaurant().map { restaurant ->
			restaurant.map(RestaurantWithPhotos::toDomain)
		}

	fun observeRestaurantDetail(id: String): Flow<Restaurant?> =
		restaurantsDao.observeRestaurantDetail(id).map {
			it?.toDomain()
		}
}
