package com.dactyl.restaurants.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.dactyl.restaurants.R
import com.dactyl.restaurants.ui.navigation.RestaurantsDestinations.RESTAURANTS_LIST_ROUTE
import com.dactyl.restaurants.ui.navigation.RestaurantsDestinations.RESTAURANTS_MAP_ROUTE

object RestaurantsDestinations {
	const val RESTAURANTS_LIST_ROUTE = "mrestaurants_list"
	const val RESTAURANT_DETAIL_ROUTE = "restaurant_detail"
	const val RESTAURANTS_MAP_ROUTE = "restaurants_map"
}

sealed class BottomNavigationDestinations(
	val route: String,
	@StringRes val navTitleResId: Int,
	val navIcon: ImageVector
) {
	object RestaurantsList : BottomNavigationDestinations(
		route = RESTAURANTS_LIST_ROUTE,
		navTitleResId = R.string.restaurants_list_title,
		navIcon = Icons.Default.List
	)

	object RestaurantsMap : BottomNavigationDestinations(
		route = RESTAURANTS_MAP_ROUTE,
		navTitleResId = R.string.restaurants_map_title,
		navIcon =  Icons.Rounded.LocationOn
	)
}
