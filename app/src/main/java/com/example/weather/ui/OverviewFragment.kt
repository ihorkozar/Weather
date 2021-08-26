package com.example.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weather.databinding.FragmentOverviewBinding
import com.example.weather.domain.Models
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverviewFragment: Fragment() {
    private lateinit var binding: FragmentOverviewBinding
    private val viewModel by viewModels<OverviewViewModel>()
    private var weatherResponse: Models.PostResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            weather.apply {
                weatherResponse = weather
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater)
        weatherResponse?.apply {
            binding.textView.text = name
        }
        return binding.root
    }
}