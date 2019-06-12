package com.benidobre.android.rainalarm

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    val daily: DailyForecast,
    val hourly: HourlyForecast
) {
    val rainProb
    get() = daily.data[0].precipProbability
}

@JsonClass(generateAdapter = true)
data class DailyForecast(
    val data: List<ForecastData>
)

@JsonClass(generateAdapter = true)
data class HourlyForecast(
    val summary : String,
    val data: List<ForecastData>
)

@JsonClass(generateAdapter = true)
data class ForecastData(
    val precipIntensity : Double,
    val precipIntensityMax : Double?,
    val precipIntensityMaxTime : Int?,
    val precipProbability : Double,
    val precipType : String?
)