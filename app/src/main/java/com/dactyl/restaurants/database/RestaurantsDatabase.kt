package com.dactyl.restaurants.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dactyl.restaurants.data.dao.RestaurantsDao
import com.dactyl.restaurants.data.entity.RestaurantEntity
import com.dactyl.restaurants.database.RestaurantsDatabase.Companion.DATABASE_VERSION

@Database(
	entities = [
		RestaurantEntity::class
	],
	version = DATABASE_VERSION
)
abstract class RestaurantsDatabase: RoomDatabase() {

	companion object{
		const val DATABASE_NAME = "restaurants_database"
		const val DATABASE_VERSION = 1
	}

	abstract fun getRestaurantsDao(): RestaurantsDao
}
