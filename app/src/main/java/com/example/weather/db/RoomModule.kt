package com.example.weather.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private lateinit var INSTANCE: WeatherDatabase

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context)
        : WeatherDatabase {
        synchronized(WeatherDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java, "weather"
                ).build()
            }
        }
        return INSTANCE
    }

    @Provides
    fun provideDao(db: WeatherDatabase): WeatherDao = db.dao
}