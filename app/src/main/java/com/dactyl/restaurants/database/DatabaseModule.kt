package com.dactyl.restaurants.database

import android.content.Context
import androidx.room.Room
import com.dactyl.restaurants.data.dao.RestaurantsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

	@Provides
	@Singleton
	fun provideRestaurantsDatabase(@ApplicationContext context: Context): RestaurantsDatabase =
		Room.databaseBuilder(
			context,
			RestaurantsDatabase::class.java,
			RestaurantsDatabase.DATABASE_NAME
		)
			.fallbackToDestructiveMigration()
			.build()

	@Provides
	fun provideRestaurantsDao(database: RestaurantsDatabase): RestaurantsDao = database.getRestaurantsDao()
}
