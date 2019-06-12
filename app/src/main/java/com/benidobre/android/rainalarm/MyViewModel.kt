package com.benidobre.android.rainalarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast> = _forecast

    fun load() {
        viewModelScope.launch {
            val forecast = Service.api.getForecast("37.8267","-122.4233")
            _forecast.postValue(forecast)
        }
    }
}