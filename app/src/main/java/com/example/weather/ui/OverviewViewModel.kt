package com.example.weather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.Models
import com.example.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    application: Application,
    private val repository: WeatherRepository
) : AndroidViewModel(application) {

    init {
        viewModelScope.launch {
            repository.refreshData()
        }
    }

    val weather: LiveData<Models.PostResponse> = repository.weather
}