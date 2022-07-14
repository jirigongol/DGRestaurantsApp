package com.dactyl.restaurants.model

import com.squareup.moshi.Json

data class PhotoDTOWrapper(
	@Json(name = "photo") val photos: PhotoDTO
)

data class PhotoDTO(
	@Json(name = "id") val id: String,
	@Json(name = "url") val photos: String
)
