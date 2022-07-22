package com.dactyl.restaurants.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.dactyl.restaurants.R

@Composable
fun CustomSearchBar(
	modifier: Modifier = Modifier,
	hint: String = "",
	onSearch: (String) -> Unit = {}
) {
	var text by remember {
		mutableStateOf("")
	}
	var isHintDisplayed by remember {
		mutableStateOf(hint != "")
	}

	Box(modifier = modifier) {
		BasicTextField(
			value = text,
			onValueChange = {
				text = it
				onSearch(it)
			},
			maxLines = 1,
			singleLine = true,
			textStyle = TextStyle(colorResource(id = R.color.searchBarTextColor)),
			modifier = Modifier
				.fillMaxWidth()
				.shadow(5.dp, RoundedCornerShape(6.dp))
				.background(colorResource(id = R.color.searchBarColor), RoundedCornerShape(6.dp))
				.onFocusChanged {
					isHintDisplayed = it.isFocused != true
				},
			cursorBrush = SolidColor(colorResource(id = R.color.searchBarTextColor)),
			decorationBox = { innerTextField ->
				Row(
					Modifier
						.background(
							colorResource(id = R.color.searchBarColor),
							RoundedCornerShape(6.dp)
						)
						.padding(all = 10.dp)
				) {
					Icon(Icons.Default.Search, contentDescription = null)
					Spacer(Modifier.width(10.dp))
					Box(modifier = Modifier.padding(top = 2.dp)) {
						if (isHintDisplayed) {
							Text(
								text = hint,
								modifier = Modifier,
								style = TextStyle(color = Color.LightGray)
							)
						}
						innerTextField()
					}
				}
			}
		)
	}
}
