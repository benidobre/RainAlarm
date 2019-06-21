package com.benidobre.android.rainalarm

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.benidobre.android.rainalarm.BuisinessLogic.getLastLocation
import kotlinx.coroutines.*

class SyncService : IntentService("SyncService") {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onHandleIntent(intent: Intent?) {
        Log.d("BENINOS", "onHandleIntent")
//        runBlocking {
//            Log.d("BENINOS", "coroutineScope")
//            val location = async { getLastLocation(applicationContext) }
//            val forecast = async { Service.api.getForecast(location.await()?.latitude.toString(), location.await()?.longitude.toString()) }
//            val notificationManager = applicationContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            val builder = NotificationCompat.Builder(applicationContext)
//                .setSmallIcon(android.R.drawable.arrow_down_float)
//                .setContentTitle("It will rain")
//                .setContentText("The chance to rain is ${forecast.await().rainProb}")
//                .setAutoCancel(false)
//
//            notificationManager.notify(456, builder.build())
//
//        }

        scope.launch {
            Log.d("BENINOS", "coroutineScope")
            val location = getLastLocation(applicationContext)
            val forecast = Service.api.getForecast(location?.latitude.toString(), location?.longitude.toString())
            val notificationManager = applicationContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(android.R.drawable.arrow_down_float)
                .setContentTitle("It will rain")
                .setContentText("The chance $$$ to rain is ${forecast.rainProb}")
                .setAutoCancel(false)

            notificationManager.notify(456, builder.build())
        }.invokeOnCompletion { scope.coroutineContext.cancelChildren() }
    }
}