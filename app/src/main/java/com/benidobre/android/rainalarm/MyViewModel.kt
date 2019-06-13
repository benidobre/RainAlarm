package com.benidobre.android.rainalarm

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast> = _forecast

    fun load() {
        viewModelScope.launch {
            if (ContextCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                val location = getLastLocation(getApplication())
                val forecast = Service.api.getForecast(location?.latitude.toString(), location?.longitude.toString())
                _forecast.postValue(forecast)
            } else {
                //TODO
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(context: Context) : Location? {
        return suspendCoroutine { continuation ->
            val task = LocationServices.getFusedLocationProviderClient(context).lastLocation
            task.addOnCompleteListener {
                continuation.resume(task.result)
            }
        }
    }
}