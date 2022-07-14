package com.dactyl.restaurants.model

import com.dactyl.restaurants.data.entity.PhotoEntity
import com.squareup.moshi.Json

data class PhotoDTOWrapper(
	@Json(name = "photo") val photos: PhotoDTO
)

data class PhotoDTO(
	@Json(name = "id") val id: String,
	@Json(name = "url") val url: String
)

fun PhotoDTO.toEntity() = PhotoEntity(
	id = id,
	url = url
)
