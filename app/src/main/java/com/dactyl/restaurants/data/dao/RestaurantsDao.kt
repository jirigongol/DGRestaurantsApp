package com.dactyl.restaurants.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dactyl.restaurants.data.entity.RestaurantEntity

@Dao
interface RestaurantsDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertRestaurant(entity: RestaurantEntity)
}
