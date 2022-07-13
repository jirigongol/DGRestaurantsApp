package com.dactyl.restaurants.network

import com.dactyl.restaurants.model.RestaurantsDTO
import retrofit2.http.GET

interface RestaurantApi {
	@GET
	suspend fun getRestaurants(): RestaurantsDTO
}
