package com.dactyl.restaurants.ui.restaurantdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dactyl.restaurants.R
import com.dactyl.restaurants.model.Restaurant
import com.dactyl.restaurants.ui.restaurantslist.RestaurantsListViewModel
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun RestaurantDetailScreen(viewModel: RestaurantDetailViewModel = viewModel()) {
	val viewState by viewModel.viewState.collectAsState(RestaurantDetailViewState(loading = true))

	if (viewState.loading) {
		LoadingScreen()
	} else if (viewState.error != null) {
		ErrorScreen(errorMessage = viewState.error!!)
	} else {
		viewState.restaurant?.let {
			RestaurantDetail(restaurant = it)
		}
	}
}

@Composable
fun RestaurantDetail(modifier: Modifier = Modifier, restaurant: Restaurant) {
	Column(modifier = modifier.fillMaxSize()) {
		AsyncImage(
			contentScale = ContentScale.FillBounds,
			model = restaurant.photos.firstOrNull()?.url ?: "",
			contentDescription = stringResource(id = R.string.restaurant_image),
			modifier = modifier
				.clip(MaterialTheme.shapes.medium)
		)

		Text(text = restaurant.name, fontWeight = FontWeight.Bold)
		Text(text = restaurant.cuisines)
		Text(text = restaurant.userRating.rating)

	}
}
