package com.example.weather.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkPostResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val timezone: Int,
    val name: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Coord(
    val lon: Double,
    val lat: Double
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(
    val main: String,
    val description: String
) : Parcelable