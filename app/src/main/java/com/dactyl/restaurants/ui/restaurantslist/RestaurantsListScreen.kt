package com.dactyl.restaurants.ui.restaurantslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dactyl.restaurants.R
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.ui.util.CustomSearchBar
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
			RestaurantsList(
				restaurants = viewState.restaurants,
				onRestaurantClick = navigateToRestaurantDetail,
				viewModel = viewModel
			)
	}
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun RestaurantsList(
	restaurants: List<Restaurant>,
	onRestaurantClick: (restaurantId: String) -> Unit,
	viewModel: RestaurantsListViewModel = viewModel()
) {
	val isSearching by remember { viewModel.isSearching }

	Column(modifier = Modifier.padding(16.dp)) {

		CustomSearchBar(
			hint = "Restaurant...",
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 8.dp)
		) {
			viewModel.searchRestaurantsList(it)
		}
		LazyColumn(
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			items(items = restaurants) { restaurant ->
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
}

@Composable
fun RestaurantItem(
	modifier: Modifier = Modifier,
	restaurant: Restaurant,
	onRestaurantClick: (restaurantId: String) -> Unit,
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onRestaurantClick(restaurant.id) }
	) {
		Card(shape = MaterialTheme.shapes.medium) {
			AsyncImage(
				contentScale = ContentScale.Crop,
				model = restaurant.photos.firstOrNull()?.url ?: "",
				contentDescription = stringResource(id = R.string.restaurant_image),
				modifier = modifier
					.fillMaxWidth()
					.height(160.dp)
					.clip(MaterialTheme.shapes.large)
			)
		}

		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = modifier
				.padding(top = 8.dp)
				.fillMaxWidth(),

			) {
			Text(text = restaurant.name, fontWeight = FontWeight.Bold)
			Row() {
				Image(
					painter = painterResource(id = R.drawable.ic_star_rating),
					contentDescription = stringResource(id = R.string.star_icon),
					modifier = modifier.padding(end = 6.dp)
				)
				Text(text = restaurant.userRating.rating, fontWeight = FontWeight.Bold)
			}
		}
		Text(text = restaurant.cuisines)

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
