package com.example.weather.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.Models

@Entity
data class DbBase constructor(
    @PrimaryKey(autoGenerate = true)
    var weatherId: Long,
    val timezone: Int,
    val name: String,
    @Embedded
    val coord: DbCoord,
    @Embedded
    val weather: DbWeather,
)

@Entity
data class DbCoord constructor(
    @PrimaryKey
    val lon: Double,
    val lat: Double
)

@Entity
data class DbWeather constructor(
    @PrimaryKey
    val main: String,
    val description: String
)

fun DbBase.asDomainModel(): Models.PostResponse =
    Models.PostResponse(
        timezone = timezone ?: 0,
        name = name.orEmpty(),
        coord = Models.Coord(
            lon = coord.lon ?: 0.0,
            lat = coord.lat ?: 0.0
        ),
        weather = Models.Weather(
            main = weather.main.orEmpty(),
            description = weather.description.orEmpty()
        )
    )