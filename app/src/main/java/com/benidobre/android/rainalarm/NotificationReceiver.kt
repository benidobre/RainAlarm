package com.benidobre.android.rainalarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val builder = NotificationCompat.Builder(context)
//            .setSmallIcon(android.R.drawable.arrow_down_float)
//            .setContentTitle("It will rain")
//            .setContentText("I am sure it will rain")
//            .setAutoCancel(false)
//
//        notificationManager.notify(456, builder.build())
        Log.d("BENINOS", "onReceive")
        context?.startService(Intent(context, SyncService::class.java))

    }

}
