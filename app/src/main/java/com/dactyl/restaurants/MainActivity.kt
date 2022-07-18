package com.dactyl.restaurants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dactyl.restaurants.ui.navigation.RestaurantsBottomNavigation
import com.dactyl.restaurants.ui.navigation.RestaurantsDestinations
import com.dactyl.restaurants.ui.navigation.RestaurantsNavGraph
import com.dactyl.restaurants.ui.theme.DactylTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			val navBackStackEntry by navController.currentBackStackEntryAsState()
			var bottomBarVisibility by rememberSaveable { (mutableStateOf(false)) }
			bottomBarVisibility =
				when (navBackStackEntry?.destination?.route?.substringBefore('/')) {
					RestaurantsDestinations.RESTAURANT_DETAIL_ROUTE -> false
					else -> true
				}

			DactylTestAppTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					Scaffold(
						bottomBar = {
							AnimatedVisibility(
								visible = bottomBarVisibility,
								enter = slideInVertically { it },
								exit = slideOutVertically { it },
							) {
								RestaurantsBottomNavigation(navController = navController)
							}
						}
					) { paddingValues ->
						RestaurantsNavGraph(
							modifier = Modifier.padding(paddingValues),
							navController = navController
						)
					}
				}
			}
		}
	}
}

@Composable
fun Greeting(name: String) {
	Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	DactylTestAppTheme {
		Greeting("Android")
	}
}
