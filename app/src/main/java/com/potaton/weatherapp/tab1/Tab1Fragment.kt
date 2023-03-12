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

        sharedViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            val weatherResponse = json.decodeFromString<WeatherResponse>(weatherData)

            binding.apply {
                //場所
                locationText.text = weatherResponse.city.name
                //気温
                tempText.text = weatherResponse.list[0].main.temp.roundToInt().toString() + "℃"
                //天気
                weatherText.text = weatherResponse.list[0].weather[0].description
                //雨
                rainText.text = nullCheck(weatherResponse.list[0].rain?.`3h`) + "mm"
                //雪
                snowText.text = nullCheck(weatherResponse.list[0].snow?.`3h`) + "mm"
                //湿度
                humidityText.text = weatherResponse.list[0].main.humidity.toString()
                //風向き・風速
                windText.text = weatherResponse.list[0].wind.speed.toString() + "m/s"
                //日の出 Long型を時間に直す
//                sunRiseText.text =
                //日の入
            }
        }
    }

    private fun nullCheck(d: Float?): String {
        return d?.toString() ?: "0"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}