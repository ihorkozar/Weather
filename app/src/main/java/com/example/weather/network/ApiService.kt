package com.example.weather.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    fun getPostResponseAsync(
        @Query("cityname") cityName: String,
        @Query("appid") apiKey: String
    ): Deferred<NetworkPostResponse>
}