package com.dactyl.restaurants.ui.restaurantdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dactyl.restaurants.R
import com.dactyl.restaurants.model.Restaurant
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun RestaurantDetailScreen(viewModel: RestaurantDetailViewModel = viewModel()) {
	val viewState by viewModel.viewState.collectAsState(RestaurantDetailViewState(loading = true))

	when {
		viewState.loading -> {
			LoadingScreen()
		}
		viewState.error != null -> {
			ErrorScreen(errorMessage = viewState.error!!)
		}
		else -> {
			viewState.restaurant?.let {
				RestaurantDetail(restaurant = it)
			}
		}
	}
}

@Composable
fun RestaurantDetail(modifier: Modifier = Modifier, restaurant: Restaurant) {
	Column(
		modifier = modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
	) {
		Box {
			AsyncImage(
				contentScale = ContentScale.Crop,
				model = restaurant.photos.firstOrNull()?.url ?: "",
				contentDescription = stringResource(id = R.string.restaurant_image),
				modifier = modifier
					.height(260.dp)
					.fillMaxWidth()
			)
			RestaurantInfo(restaurant = restaurant)
		}
	}

}

@Composable
fun RestaurantInfo(modifier: Modifier = Modifier, restaurant: Restaurant) {
	Card(
		modifier = modifier
			.padding(top = 220.dp)
			.fillMaxSize(),
		elevation = 0.dp,
		shape = RoundedCornerShape(topStart = 38.dp, topEnd = 38.dp)
	) {
		Column(
			modifier = modifier
				.padding(20.dp)
				.fillMaxSize()
		) {
			Text(
				text = restaurant.name,
				fontWeight = FontWeight.Bold,
				fontSize = 22.sp,
				modifier = modifier.padding(bottom = 20.dp)
			)
			Row(modifier = modifier.padding(bottom = 16.dp)) {
				Image(
					painter = painterResource(id = R.drawable.ic_star_rating),
					contentDescription = stringResource(id = R.string.star_icon),
					modifier = modifier.padding(end = 12.dp)
				)
				Text(text = restaurant.userRating.rating)
			}
			Row(modifier = modifier.padding(bottom = 16.dp)) {
				Image(
					painter = painterResource(id = R.drawable.ic_restaurant),
					contentDescription = stringResource(id = R.string.restaurant_icon),
					modifier = modifier.padding(end = 12.dp)
				)
				Text(text = restaurant.cuisines)
			}
			Row(modifier = modifier.padding(bottom = 40.dp)) {
				Icon(
					Icons.Outlined.LocationOn,
					contentDescription = stringResource(id = R.string.location_icon),
					modifier = modifier.padding(end = 12.dp),
				)
				Text(text = restaurant.location.address)
			}
			LazyRow(
				contentPadding = PaddingValues(12.dp),
				horizontalArrangement = Arrangement.spacedBy(12.dp),
				modifier = modifier.fillMaxSize()
			) {
				items(restaurant.photos.drop(1)) { photo ->
					AsyncImage(
						contentScale = ContentScale.Crop,
						model = photo.url,
						contentDescription = stringResource(id = R.string.restaurant_image),
						modifier = modifier
							.size(width = 200.dp, height = 240.dp)
							.clip(MaterialTheme.shapes.large)
					)
				}
			}
		}
	}
}




