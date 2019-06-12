package com.benidobre.android.rainalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MyViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.load()
        viewModel.forecast.observe(this, Observer {
            Toast.makeText(this, "The chance to rain is ${it.rainProb}", Toast.LENGTH_LONG).show()
        })
    }
}