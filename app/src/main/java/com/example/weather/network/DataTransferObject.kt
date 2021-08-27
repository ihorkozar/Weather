package com.example.weather.network

import android.os.Parcelable
import com.example.weather.db.DbBase
import com.example.weather.db.DbCoord
import com.example.weather.db.DbWeather
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPostResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val timezone: Int,
    val name: String
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Coord(
    val lon: Double,
    val lat: Double
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(
    val main: String,
    val description: String
): Parcelable

fun NetworkPostResponse.asDatabaseModel(): DbBase = DbBase(
    weatherId = 0,
    timezone = timezone,
    name = name,
    coord = DbCoord(
        lon = coord.lon,
        lat = coord.lat
    ),
    weather = DbWeather(
        main = weather.first().main,
        description = weather.first().description
    )
)