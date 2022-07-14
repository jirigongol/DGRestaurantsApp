package com.dactyl.restaurants.ui.restaurantslist

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ExperimentalFoundationApi
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

@Composable
fun RestaurantsListScreen(viewModel: RestaurantsListViewModel = viewModel()) {
	val viewState by viewModel.viewState.collectAsState(RestaurantsListViewState(restaurants = emptyList()))

	if (viewState.loading) {
	} else if (viewState.error != null) {
	} else {
		viewState.restaurants?.let {
			RestaurantsList(restaurants = it)
		}
	}
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun RestaurantsList(modifier: Modifier = Modifier, restaurants: List<Restaurant>) {
	LazyColumn(
		contentPadding = PaddingValues(12.dp),
		verticalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(restaurants) { restaurant ->
			val state = remember {
				MutableTransitionState(false).apply {
					// Start the animation immediately.
					targetState = true
				}
			}
			RestaurantItem(restaurant = restaurant)
		}
	}
}

@Composable
fun RestaurantItem(modifier: Modifier = Modifier, restaurant: Restaurant) {
	Card {
		Row(
			modifier = modifier
				.fillMaxWidth()
				.padding(all = 6.dp)
		) {

//			AsyncImage(
//				contentScale = ContentScale.FillBounds,
//				model = "${restaurant.photos[0].photo.url}",
//				contentDescription = stringResource(id = R.string.restaurant_image),
//				modifier = modifier
//					.size(width = 80.dp, height = 120.dp)
//					.clip(MaterialTheme.shapes.medium)
//			)
			Column(
				modifier = modifier.padding(start = 12.dp, top = 16.dp)
			) {
				Text(
					text = restaurant.name, fontWeight = FontWeight.Bold
				)
				Text(text = restaurant.cuisines)
				Text(text = restaurant.userRating.rating)
				Text(text = restaurant.location.address)
				Text(text = restaurant.location.longitude)
				Text(text = restaurant.location.latitude)
			}
		}
	}
}
