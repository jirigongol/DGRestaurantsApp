package com.dactyl.restaurants.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dactyl.restaurants.model.Location
import com.dactyl.restaurants.model.PhotosX
import com.dactyl.restaurants.model.UserRating

@Entity(tableName = "restaurant")
class RestaurantEntity(
	@PrimaryKey
	@ColumnInfo(name = "name") val name: String,
	@ColumnInfo(name = "cuisines") val cuisines: String,
	@ColumnInfo(name = "user_rating")val userRating: UserRating,
	@ColumnInfo(name = "location")val location: Location,
	@ColumnInfo(name = "photos") val photos: List<PhotosX>

	)
