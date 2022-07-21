package com.dactyl.restaurants

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dactyl.restaurants.ui.navigation.RestaurantsBottomNavigation
import com.dactyl.restaurants.ui.navigation.RestaurantsDestinations
import com.dactyl.restaurants.ui.navigation.RestaurantsNavGraph
import com.dactyl.restaurants.ui.restaurantslist.RestaurantsListViewModel
import com.dactyl.restaurants.ui.restaurantsmap.RestaurantsMapViewModel
import com.dactyl.restaurants.ui.theme.DactylTestAppTheme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val FASTEST_INTERVAL_MILLIS = 10000L
private const val INTERVAL_MILLIS = 60000L

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//	val mapViewModel by viewModels<RestaurantsListViewModel>()

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

//	override fun onStart() {
//		super.onStart()
//		Log.d("TAG", "ON START")
//		start()
//	}
//
//	override fun onStop() {
//		super.onStop()
//		Log.d("TAG", "ON STOP")
//		stop()
//	}

	//START
	private var job: Job? = null

	fun start() {
		Log.d("TAG", "START")
		if (job != null) return
		Log.d("TAG", "PASS IF")

		val locationRequest = createLocationRequest()
		job = CoroutineScope(Dispatchers.Main).launch {
			Log.d("TAG", "ENTER job")

			locationFlow(locationRequest).collect { location ->
				val lat = location.latitude
				val lon = location.longitude
				Log.d("received location from gms: [$lat;$lon]", "PASS IF")
//				mapViewModel.saveLocation(lat, lon)

			}
		}
	}

	fun stop() {
		job?.cancel()
	}

	private fun createLocationRequest() = LocationRequest.create()
		.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
		.setFastestInterval(FASTEST_INTERVAL_MILLIS)
		.setInterval(INTERVAL_MILLIS)

	private suspend fun locationFlow(request: LocationRequest) =
		channelFlow<Location> {
			Log.d("TAG", "locationFLow")

			val locationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)

			val locationCallback = object : LocationCallback() {
				override fun onLocationResult(result: LocationResult) {
					result.locations.forEach { location ->
						trySend(location)
					}
				}
			}

			//Check permission
			if (ActivityCompat.checkSelfPermission(
					this@MainActivity,
					Manifest.permission.ACCESS_FINE_LOCATION
				)
				!= PackageManager.PERMISSION_GRANTED &&
				ActivityCompat.checkSelfPermission(
					this@MainActivity,
					Manifest.permission.ACCESS_COARSE_LOCATION
				)
				!= PackageManager.PERMISSION_GRANTED
			)
				Log.d("TAG", "Check point")

			locationClient.lastLocation.await<Location?>()?.let { send(it) }
			locationClient.requestLocationUpdates(request, locationCallback, null).await()
			awaitClose { locationClient.removeLocationUpdates(locationCallback) }
		}

	//KONEC
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
