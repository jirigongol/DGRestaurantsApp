package com.dactyl.restaurants.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dactyl.restaurants.model.Photo

@Entity(tableName = "photo")
class PhotoEntity(
	@PrimaryKey
	@ColumnInfo(name = "photo_id") val photoId: String,
	@ColumnInfo(name = "restaurant_id") val restaurantId: String,
	@ColumnInfo(name = "url") val url: String,
)

fun PhotoEntity.toDomain() = Photo(
	id = photoId,
	url = url
)
