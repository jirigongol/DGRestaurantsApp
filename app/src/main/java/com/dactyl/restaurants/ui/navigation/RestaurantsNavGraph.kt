package com.dactyl.restaurants.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dactyl.restaurants.ui.restaurantdetail.RestaurantDetailScreen
import com.dactyl.restaurants.ui.restaurantslist.RestaurantsListScreen
import com.dactyl.restaurants.ui.restaurantsmap.RestaurantsMapScreen

@Composable
fun RestaurantsNavGraph(
	modifier: Modifier = Modifier,
	navController: NavHostController = rememberNavController()
) {
	NavHost(
		modifier = modifier,
		navController = navController,
		startDestination = RestaurantsDestinations.RESTAURANTS_LIST_ROUTE
	) {
		composable(RestaurantsDestinations.RESTAURANTS_LIST_ROUTE) {
			RestaurantsListScreen(
				navigateToRestaurantDetail = { restaurantId ->
					navController.navigate("${RestaurantsDestinations.RESTAURANT_DETAIL_ROUTE}/$restaurantId")
				},
				viewModel = hiltViewModel()
			)
		}

		composable(
			route = "${RestaurantsDestinations.RESTAURANT_DETAIL_ROUTE}/{${RestaurantsNavArguments.RESTAURANT_ID_KEY}}",
			arguments = listOf(
				navArgument(RestaurantsNavArguments.RESTAURANT_ID_KEY) {
					type = NavType.StringType
				}
			)
		) {
			RestaurantDetailScreen(viewModel = hiltViewModel())
		}

		composable(
			route = RestaurantsDestinations.RESTAURANTS_MAP_ROUTE
		) {
			RestaurantsMapScreen()
		}

	}
}
