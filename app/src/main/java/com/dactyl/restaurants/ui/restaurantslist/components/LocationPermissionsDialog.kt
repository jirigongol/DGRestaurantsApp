package com.dactyl.restaurants.ui.restaurantslist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dactyl.restaurants.R

@Composable
fun LocationPermissionsDialog(
	positiveText: String,
	negative: () -> Unit,
	positive: () -> Unit
) {
	AlertDialog(
		onDismissRequest = negative,
		title = {
			Text(
				text = "Permissions required",
				style = MaterialTheme.typography.h5
			)
		},
		text = {
			Text(
				text = "To fully benefit from Restaurants app, you need to allow Precise location permissions",
				style = MaterialTheme.typography.subtitle2
			)
		},
		buttons = {
			Row(
				modifier = Modifier
					.padding(16.dp)
					.height(intrinsicSize = IntrinsicSize.Min),
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Button(
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight(),
					onClick = negative,
					colors = ButtonDefaults.buttonColors(
						backgroundColor = MaterialTheme.colors.error,
						contentColor = MaterialTheme.colors.onError
					)
				) {
					Text(text = "Dismiss".uppercase(), textAlign = TextAlign.Center)
				}
				Button(
					modifier = Modifier
						.weight(1f)
						.fillMaxHeight(),
					onClick = positive,
					colors = ButtonDefaults.buttonColors(
						backgroundColor = colorResource(id = R.color.grantPermissions),
						contentColor = MaterialTheme.colors.onError
					)
				) {
					Text(text = positiveText.uppercase(), textAlign = TextAlign.Center)
				}
			}
		}
	)
}
