package com.dactyl.restaurants.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dactyl.restaurants.model.Location
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.model.UserRating

@Entity(tableName = "restaurant")
class RestaurantEntity(
	@PrimaryKey
	@ColumnInfo(name = "id") val id: String,
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "cuisines") val cuisines: String,
	@ColumnInfo(name = "aggregate_rating") val aggregateRating: String,
	@ColumnInfo(name = "rating_color") val ratingColor: String,
	@ColumnInfo(name = "address") val address: String,
	@ColumnInfo(name = "latitude") val latitude: String,
	@ColumnInfo(name = "longitude") val longitude: String
)

fun RestaurantEntity.toDomain() = Restaurant(
	name = name,
	cuisines = cuisines,
	userRating = UserRating(aggregateRating, ratingColor),
	location = Location(address, latitude, longitude),
)
