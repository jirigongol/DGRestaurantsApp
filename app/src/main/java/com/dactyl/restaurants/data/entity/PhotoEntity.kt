package com.dactyl.restaurants.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
class PhotoEntity(
	@PrimaryKey
	@ColumnInfo(name = "id") val id: String,
	@ColumnInfo(name = "url") val url: String,
)
