package com.dactyl.restaurants.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.dactyl.restaurants.model.Location
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.model.UserRating

data class RestaurantWithPhotos(
	@Embedded val restaurantEntity: RestaurantEntity,

	@Relation(
		parentColumn = "id",
		entityColumn = "restaurant_id"
	)
	val photos: List<PhotoEntity>
)

fun RestaurantWithPhotos.toDomain() = Restaurant(
	id = restaurantEntity.id,
	name = restaurantEntity.name,
	cuisines = restaurantEntity.cuisines,
	userRating = UserRating(
		restaurantEntity.aggregateRating,
		restaurantEntity.ratingColor
	),
	location = Location(
		restaurantEntity.address,
		restaurantEntity.latitude,
		restaurantEntity.longitude
	),
	photos = photos.map { it.toDomain() }
)
