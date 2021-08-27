package com.example.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weather.db.WeatherDao
import com.example.weather.db.asDomainModel
import com.example.weather.domain.Models
import com.example.weather.network.NetworkPostResponse
import com.example.weather.network.WeatherApi
import com.example.weather.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val dao: WeatherDao,
    private val apiService: WeatherApi
) {

    val weather: LiveData<Models.PostResponse> = Transformations.map(
        dao.getData()
    ) {
        it.asDomainModel()
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val postResponse: NetworkPostResponse = apiService.getPostResponseAsync("London", API_KEY).await()
            dao.insert(postResponse.asDatabaseModel())
        }
    }

    companion object {
        private const val API_KEY = "69fa176e0b9e4c0aebe0cea100aa629d"
    }
}