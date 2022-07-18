package com.dactyl.restaurants.ui.restaurantslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dactyl.restaurants.R
import com.dactyl.restaurants.model.Restaurant
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RestaurantsListScreen(
	navigateToRestaurantDetail: (restaurantId: String) -> Unit,
	viewModel: RestaurantsListViewModel = viewModel()
) {
	val viewState by viewModel.viewState.collectAsState(RestaurantsListViewState(restaurants = emptyList()))

	if (viewState.loading) {
		LoadingScreen()
	} else if (viewState.error != null) {
		ErrorScreen(errorMessage = viewState.error!!)
	} else {
		viewState.restaurants?.let {
			RestaurantsList(restaurants = it, onRestaurantClick = navigateToRestaurantDetail)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun RestaurantsList(
	restaurants: List<Restaurant>,
	onRestaurantClick: (restaurantId: String) -> Unit,
) {
	LazyColumn(
		contentPadding = PaddingValues(12.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(items = restaurants.sortedBy {
			distanceInKm(
				49.1959450000,
				16.6117270000,
				it.location.latitude.toDouble(),
				it.location.longitude.toDouble()
			)
		}) { restaurant ->
//			Log.e("TAG", distanceInKm(49.1959450000, 16.6117270000, restaurant.location.latitude.toDouble(), restaurant.location.longitude.toDouble()).toString() + restaurant.name)
			val state = remember {
				MutableTransitionState(false).apply {
					targetState = true
				}
			}
			AnimatedVisibility(
				visibleState = state,
				enter = fadeIn(animationSpec = tween(300)) + scaleIn(animationSpec = tween(300))
			) {
				RestaurantItem(
					restaurant = restaurant, onRestaurantClick = onRestaurantClick
				)
			}
		}
	}
}

@Composable
fun RestaurantItem(
	modifier: Modifier = Modifier,
	restaurant: Restaurant,
	onRestaurantClick: (restaurantId: String) -> Unit,
) {
	Card(modifier = modifier.clickable { onRestaurantClick(restaurant.id) }) {
		Row(
			modifier = modifier
				.fillMaxWidth()
				.padding(all = 6.dp)
		) {

			AsyncImage(
				contentScale = ContentScale.FillBounds,
				model = restaurant.photos.firstOrNull()?.url ?: "",
				contentDescription = stringResource(id = R.string.restaurant_image),
				modifier = modifier
					.size(width = 80.dp, height = 120.dp)
					.clip(MaterialTheme.shapes.medium)
			)
			Column(
				modifier = modifier.padding(start = 12.dp, top = 16.dp)
			) {
				Text(text = restaurant.name, fontWeight = FontWeight.Bold)
				Text(text = restaurant.cuisines)
				Text(text = restaurant.userRating.rating)
			}
		}
	}
}

fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
	val theta = lon1 - lon2
	var dist =
		sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(
			deg2rad(lat2)
		) * cos(deg2rad(theta))
	dist = acos(dist)
	dist = rad2deg(dist)
	dist *= 60 * 1.1515
	dist *= 1.609344
	return dist
}

private fun deg2rad(deg: Double): Double {
	return deg * Math.PI / 180.0
}

private fun rad2deg(rad: Double): Double {
	return rad * 180.0 / Math.PI
}
