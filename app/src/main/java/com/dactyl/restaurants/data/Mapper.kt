package com.dactyl.restaurants.data

interface Mapper<I, O> {
	fun map(from: I): O
}
