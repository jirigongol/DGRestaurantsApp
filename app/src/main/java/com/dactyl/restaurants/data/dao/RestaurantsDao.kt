package com.dactyl.restaurants.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dactyl.restaurants.data.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantsDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertRestaurant(entity: List<RestaurantEntity>)

	@Query("SELECT * FROM restaurant")
	fun observeMovieDetail(): Flow<List<RestaurantEntity>>


}
