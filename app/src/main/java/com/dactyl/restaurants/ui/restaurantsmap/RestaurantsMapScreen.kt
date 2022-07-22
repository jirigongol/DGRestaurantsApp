package com.dactyl.restaurants.ui.restaurantsmap

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dactyl.restaurants.R
import com.dactyl.restaurants.model.Restaurant
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun RestaurantsMapScreen(viewModel: RestaurantsMapViewModel = viewModel()) {
	val viewState by viewModel.viewState.collectAsState(RestaurantsMapViewState(restaurants = emptyList()))

	when {
		viewState.loading -> {
			LoadingScreen()
		}
		viewState.error != null -> {
			ErrorScreen(errorMessage = viewState.error!!)
		}
		else -> {
			RestaurantsMap(
				restaurants = viewState.restaurants,
				userCurrentPosition = viewState.myPosition
			)
		}
	}
}

@Composable
fun RestaurantsMap(
	restaurants: List<Restaurant>,
	userCurrentPosition: LatLng,
) {
	val scaffoldState = rememberScaffoldState()

	Log.d("xxxx", "RestaurantsMap: $userCurrentPosition")
	val cameraPositionState = rememberCameraPositionState {
		position = CameraPosition.fromLatLngZoom(userCurrentPosition, 16f)
	}

	Scaffold(
		scaffoldState = scaffoldState,
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					cameraPositionState.position =
						CameraPosition.fromLatLngZoom(userCurrentPosition, 16f)
				},
				backgroundColor = MaterialTheme.colors.background,
				contentColor = MaterialTheme.colors.primary,
				modifier = Modifier.padding(bottom = 48.dp)
			) {
				Icon(
					imageVector = Icons.Default.MyLocation,
					tint = colorResource(id = R.color.myLocation),
					contentDescription = stringResource(id = R.string.fab_description)
				)
			}
		}
	) {
		cameraPositionState.position = CameraPosition.fromLatLngZoom(userCurrentPosition, 16f)
		GoogleMap(
			modifier = Modifier.fillMaxSize(),
			cameraPositionState = cameraPositionState,
			uiSettings = MapUiSettings(zoomControlsEnabled = false)
		) {
			Marker(
				position = userCurrentPosition,
				title = "Your Location",
				icon = BitmapDescriptorFactory.defaultMarker(
					BitmapDescriptorFactory.HUE_GREEN
				)
			)
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
}
