package com.dactyl.restaurants.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dactyl.restaurants.data.entity.PhotoEntity
import com.dactyl.restaurants.data.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantsDao {

	@Transaction
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertRestaurant(entity: RestaurantEntity)

	@Query("SELECT * FROM restaurant")
	fun observeMovieDetail(): Flow<List<RestaurantEntity>>

	@Transaction
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertPhotos(entity: List<PhotoEntity>)
}
