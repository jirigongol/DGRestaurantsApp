package com.dactyl.restaurants.network

import com.dactyl.restaurants.model.RestaurantDTO
import com.squareup.moshi.Json
import retrofit2.http.GET

interface RestaurantApi {
	@GET("restaurants.json")
	suspend fun getRestaurants(): RestaurantsResponse
}

data class RestaurantsResponse(
	@Json(name = "restaurants")
	val restaurants: List<RestaurantDTOWrapper>,
	@Json(name = "results_found")
	val results_found: Int,
	@Json(name = "results_shown")
	val results_shown: Int,
	@Json(name = "results_start")
	val results_start: Int
)

data class RestaurantDTOWrapper(
	@Json(name = "restaurant")
	val restaurant: RestaurantDTO
)
