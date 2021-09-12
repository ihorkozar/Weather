package com.example.weather.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getPostResponseAsync(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): NetworkPostResponse
}