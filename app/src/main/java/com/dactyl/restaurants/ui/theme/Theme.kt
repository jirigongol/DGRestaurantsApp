package com.dactyl.restaurants.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(

	primary = md_theme_light_primary,
	onPrimary = md_theme_light_onPrimary,
	secondary = md_theme_light_secondary,
	onSecondary = md_theme_light_onSecondary,
	error = md_theme_light_error,
	onError = md_theme_light_onError,
	background = md_theme_light_background,
	onBackground = md_theme_light_onBackground,
	surface = md_theme_light_surface,
	onSurface = md_theme_light_onSurface,
)
private val DarkThemeColors = darkColors(

	primary = md_theme_dark_primary,
	onPrimary = md_theme_dark_onPrimary,
	secondary = md_theme_dark_secondary,
	onSecondary = md_theme_dark_onSecondary,
	error = md_theme_dark_error,
	onError = md_theme_dark_onError,
	background = md_theme_dark_background,
	onBackground = md_theme_dark_onBackground,
	surface = md_theme_dark_surface,
	onSurface = md_theme_dark_onSurface,
)

@Composable
fun DactylTestAppTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable () -> Unit
) {
	val colors = if (darkTheme) {
		DarkThemeColors
	} else {
		LightThemeColors
	}

	MaterialTheme(
		colors = colors,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}
