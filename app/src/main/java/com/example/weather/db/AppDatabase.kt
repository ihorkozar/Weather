package com.example.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbWeather::class, DbBase::class, DbCoord::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val dao: WeatherDao
}