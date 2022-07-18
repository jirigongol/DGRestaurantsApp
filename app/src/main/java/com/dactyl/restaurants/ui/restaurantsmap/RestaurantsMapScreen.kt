package com.dactyl.restaurants.ui.restaurantsmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dactyl.restaurants.model.Restaurant
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun RestaurantsMapScreen(viewModel: RestaurantsMapViewModel = viewModel()) {
	val viewState by viewModel.viewState.collectAsState(RestaurantsMapViewState(restaurants = emptyList()))

	if (viewState.loading) {
		LoadingScreen()
	} else if (viewState.error != null) {
		ErrorScreen(errorMessage = viewState.error!!)
	} else {
		viewState.restaurants?.let {
			RestaurantsMap(restaurants = it)
		}
	}
}

@Composable
fun RestaurantsMap(
	restaurants: List<Restaurant>,
) {

	var location = LatLng(
		49.2317480000,
		16.5901280000
	)
	val cameraPositionState = rememberCameraPositionState {
		position = CameraPosition.fromLatLngZoom(location, 10f)
	}
	GoogleMap(
		modifier = Modifier.fillMaxSize(),
		cameraPositionState = cameraPositionState
	) {

// Create multiple markers from list
		restaurants.forEach { restaurant ->

			Marker(
				position = LatLng(
					restaurant.location.latitude.toDouble(),
					restaurant.location.longitude.toDouble()
				),
				title = restaurant.name,
				snippet = "Title Snippet"
			)
		}
	}
}


