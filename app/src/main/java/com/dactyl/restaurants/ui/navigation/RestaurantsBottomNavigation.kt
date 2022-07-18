package com.dactyl.restaurants.ui.navigation


import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun RestaurantsBottomNavigation(
	navController: NavHostController = rememberNavController()
){
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination
	BottomNavigation {
		listOf(
			BottomNavigationDestinations.RestaurantsList,
			BottomNavigationDestinations.RestaurantsMap
		).forEach { screen ->
			BottomNavigationItem(
				icon = { Icon(screen.navIcon, contentDescription = null) },
				label = { Text(stringResource(screen.navTitleResId)) },
				selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
				onClick = {
					navController.navigate(screen.route) {
						popUpTo(navController.graph.findStartDestination().id) {
							saveState = true
						}
						launchSingleTop = true
						restoreState = true
					}
				}
			)
		}
	}
}
