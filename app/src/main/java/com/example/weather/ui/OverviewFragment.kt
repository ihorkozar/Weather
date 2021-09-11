package com.example.weather.ui

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weather.BoundService
import com.example.weather.databinding.FragmentOverviewBinding
import com.example.weather.network.NetworkPostResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding: FragmentOverviewBinding
        get() = requireNotNull(_binding)

    lateinit var boundService: BoundService
    var bound = false

    private val broadcast: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.getParcelableExtra<NetworkPostResponse>("data")
            data?.let {
                binding.tvTimeZone.text = "timezone: ${it.timezone}"
                binding.tvName.text = "city: ${it.name}"
                binding.tvCoordLat.text = "lat: ${it.coord.lat}"
                binding.tvCoordLon.text = "lon: ${it.coord.lon}"
                binding.tvMain.text = it.weather.first().main
                binding.tvDescription.text = it.weather.first().description
            }
        }
    }

    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder: BoundService.LocalBinder = service as BoundService.LocalBinder
            boundService = binder.getService
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), BoundService::class.java).also {
            activity?.bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(broadcast, IntentFilter("myBroadcast"))
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(broadcast)
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(connection)
        bound = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}