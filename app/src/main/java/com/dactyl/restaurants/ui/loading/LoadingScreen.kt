package com.strv.movies.ui.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dactyl.restaurants.ui.theme.DactylTestAppTheme

@Composable
fun LoadingScreen(
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		CircularProgressIndicator()
	}
}

@Preview
@Composable
private fun LoadingScreenPreview() {
	DactylTestAppTheme() {
		Surface {
			LoadingScreen()
		}
	}
}
