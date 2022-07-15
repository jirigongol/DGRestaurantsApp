package com.dactyl.restaurants.model

import com.dactyl.restaurants.data.entity.PhotoEntity
import com.squareup.moshi.Json

data class PhotoDTOWrapper(
	@Json(name = "photo") val photos: PhotoDTO
)

data class Photo(
	val id: String,
	val url: String
)

data class PhotoDTO(
	@Json(name = "id") val id: String,
	@Json(name = "url") val url: String
)

fun PhotoDTO.toEntity(restaurantId: String) = PhotoEntity(
	photoId = id,
	restaurantId = restaurantId,
	url = url
)
