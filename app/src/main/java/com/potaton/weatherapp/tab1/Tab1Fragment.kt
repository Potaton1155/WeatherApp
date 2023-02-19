package com.potaton.weatherapp.tab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.potaton.weatherapp.R
import com.potaton.weatherapp.SharedViewModel
import com.potaton.weatherapp.WeatherResponse
import com.potaton.weatherapp.databinding.FragmentTab1Binding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Tab1Fragment : Fragment(R.layout.fragment_tab1) {
    private var _binding: FragmentTab1Binding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTab1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.data.observe(viewLifecycleOwner) { data ->
            val weatherResponse = Json { ignoreUnknownKeys = true }.decodeFromString<WeatherResponse>(data)
//            binding.weatherText.text = weatherResponse.list[0].weather[0].description
//            binding.locationText.text = weatherResponse.city.name


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}