package com.example.weather

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather.network.NetworkPostResponse
import com.example.weather.network.WeatherApi
import com.example.weather.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BoundService : Service() {
    @Inject
    lateinit var repository: WeatherRepository
    @Inject
    lateinit var apiService: WeatherApi

    // Binder given to clients
    private val binder = LocalBinder()

    lateinit var postResponse: NetworkPostResponse

    inner class LocalBinder : Binder() {
        val getService: BoundService = this@BoundService
    }

    private fun postData(postResponse: NetworkPostResponse){
        val intent = Intent("myBroadcast")
        intent.putExtra("data", postResponse)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d("onBind", "kek")
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            withContext(Dispatchers.IO){
                postResponse = apiService
                    .getPostResponseAsync("London", WeatherRepository.API_KEY)
                    .await()
                postData(postResponse)
                //repository.refreshData()
            }
        }
        return binder
    }
}