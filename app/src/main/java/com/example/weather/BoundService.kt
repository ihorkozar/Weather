package com.example.weather

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.weather.network.NetworkPostResponse
import com.example.weather.network.WeatherApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BoundService : Service() {
    @Inject
    lateinit var apiService: WeatherApi

    // Binder given to clients
    private val binder = LocalBinder()

    private var postResponse: NetworkPostResponse? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    inner class LocalBinder : Binder() {
        val getService: BoundService = this@BoundService
    }

    private fun postData(postResponse: NetworkPostResponse) {
        val intent = Intent("myBroadcast")
        intent.putExtra("data", postResponse)
        this.sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.d("onBind", "kek")
        scope.launch {
            postResponse = apiService.getPostResponseAsync("London", API_KEY)
            postResponse?.let { postData(it) }
        }
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    companion object {
        const val API_KEY = "69fa176e0b9e4c0aebe0cea100aa629d"
    }
}