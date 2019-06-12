package com.benidobre.android.rainalarm

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "fb8ae8019a200f9a6582128556388357"

object Service {
    val api : WeatherApiClient by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.darksky.net/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        // Create Retrofit client
        return@lazy retrofit.create(WeatherApiClient::class.java)
    }
}

interface WeatherApiClient {
    @GET("/forecast/$API_KEY/{lat},{long}")
    suspend fun getForecast(@Path("lat") lat: String, @Path("long") long: String): Forecast
}