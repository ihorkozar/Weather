package com.example.weather.network

import com.example.weather.db.DbBase
import com.example.weather.db.DbCoord
import com.example.weather.db.DbWeather
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPostResponse(
    val coord: Coord,
    val weather: Weather,
    val timezone: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Coord(
    val lon: Double,
    val lat: Double
)

@JsonClass(generateAdapter = true)
data class Weather(
    val main: String,
    val description: String
)

fun NetworkPostResponse.asDatabaseModel(): DbBase = DbBase(
    weatherId = 0,
    timezone = timezone,
    name = name,
    coord = DbCoord(
        lon = coord.lon,
        lat = coord.lat
    ),
    weather = DbWeather(
        main = weather.main,
        description = weather.description
    )
)