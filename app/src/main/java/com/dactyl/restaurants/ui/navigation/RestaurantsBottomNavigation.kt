package com.dactyl.restaurants.ui.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun RestaurantsBottomNavigation(
	navController: NavHostController = rememberNavController()
) {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentDestination = navBackStackEntry?.destination


	BottomNavigation(
		modifier = Modifier.graphicsLayer {
			clip = true
			shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
			shadowElevation = 50f
		},
		backgroundColor = MaterialTheme.colors.background,
		contentColor = contentColorFor(MaterialTheme.colors.background)
	) {
		listOf(
			BottomNavigationDestinations.RestaurantsList,
			BottomNavigationDestinations.RestaurantsMap
		).forEach { screen ->
			BottomNavigationItem(
				icon = {
					Icon(
						screen.navIcon,
						contentDescription = null,
						modifier = Modifier.size(24.dp),
					)
				},
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
				},
				unselectedContentColor = Color.Gray,
				selectedContentColor = MaterialTheme.colors.primary,
			)
		}
	}
}
