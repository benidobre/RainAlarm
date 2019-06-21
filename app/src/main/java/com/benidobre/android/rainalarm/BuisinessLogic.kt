package com.benidobre.android.rainalarm

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object BuisinessLogic {
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