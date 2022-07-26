package com.dactyl.restaurants.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private const val FASTEST_INTERVAL_MILLIS = 10000L
private const val INTERVAL_MILLIS = 60000L

@Singleton
class LocationInteractor @Inject constructor(
	@ApplicationContext private val context: Context
) {

	private var locationJob: Job? = null
	private val processScope = ProcessLifecycleOwner.get().lifecycleScope

	private val _locationData = MutableStateFlow<Location?>(null)
	val locationData = _locationData.filterNotNull()//.debounce(2_500L)

	@SuppressLint("MissingPermission")
	fun start() {
		if (locationJob != null) return

		val locationRequest = createLocationRequest()
		locationJob = processScope.launch {
			locationFlow(locationRequest).collect { location ->
				_locationData.update { location }
			}
		}
	}

	fun stop() {
		locationJob?.cancel()
		locationJob = null
	}

	private fun createLocationRequest() = LocationRequest.create()
		.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
		.setFastestInterval(FASTEST_INTERVAL_MILLIS)
		.setInterval(INTERVAL_MILLIS)

	@SuppressLint("MissingPermission")
	private suspend fun locationFlow(request: LocationRequest) =
		channelFlow<Location> {
			val locationClient = LocationServices.getFusedLocationProviderClient(context)
			val locationCallback = object : LocationCallback() {
				override fun onLocationResult(result: LocationResult) {
					result.locations.forEach { location ->
						trySend(location)
					}
				}
			}

			locationClient.lastLocation.await<Location?>()?.let { send(it) }
			locationClient.requestLocationUpdates(request, locationCallback, null).await()
			awaitClose { locationClient.removeLocationUpdates(locationCallback) }
		}
}
