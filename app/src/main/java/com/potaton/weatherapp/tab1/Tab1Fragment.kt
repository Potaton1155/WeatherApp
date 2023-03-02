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
import kotlin.math.roundToInt
import kotlin.math.sign

class Tab1Fragment : Fragment(R.layout.fragment_tab1) {
    private var _binding: FragmentTab1Binding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTab1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.data.observe(viewLifecycleOwner) { data ->
            val weatherResponse = json.decodeFromString<WeatherResponse>(data)

            binding.apply {
                //場所
                locationText.text = weatherResponse.city.name
                //気温
                tempText.text = weatherResponse.list[0].main.temp.roundToInt().toString() + "℃"
                //天気
                weatherText.text = weatherResponse.list[0].weather[0].description
                //雨
                rainText.text = nullCheck(weatherResponse.list[0].rain?.three_hour_rain) + "mm"
                //雪
                snowText.text = nullCheck(weatherResponse.list[0].snow?.three_hour_snow) + "mm"
                //湿度
                humidityText.text = weatherResponse.list[0].main.humidity.toString()
            }
        }
    }

    private fun nullCheck(d: Double?): String {
        val result = if (d != null) {
            toString()
        } else {
            ""
        }
        return result
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}