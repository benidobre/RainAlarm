package com.benidobre.android.rainalarm

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askForLocationPermission()

        button.setOnClickListener {
            Log.d("BENINOS", "onClickListener")
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY,10)
            calendar.set(Calendar.MINUTE,50)
            calendar.set(Calendar.SECOND,30)
            val intent = Intent(applicationContext, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(applicationContext, 456, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        }

        viewModel.load()
        viewModel.forecast.observe(this, Observer {
            Toast.makeText(this, "The chance to rain is ${it.rainProb}", Toast.LENGTH_LONG).show()
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    viewModel.load()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun askForLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("We need to show the weather for your specific location. Isn't that what you want?")
                    .setPositiveButton("ok") { _, _ ->
                        ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                            MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
                    }
                    .setNegativeButton("cancel") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 101
    }
}