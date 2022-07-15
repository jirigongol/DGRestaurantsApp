package com.dactyl.restaurants.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
