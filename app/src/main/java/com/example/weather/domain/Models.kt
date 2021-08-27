package com.example.weather.domain

class Models {
    data class PostResponse(
        val coord: Coord,
        val weather: List<Weather>,
        val timezone: Int,
        val name: String
    )

    data class Coord(
        val lon: Double,
        val lat: Double
    )

    data class Weather(
        val main: String,
        val description: String
    )
}