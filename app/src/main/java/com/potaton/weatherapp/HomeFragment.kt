package com.potaton.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import com.potaton.weatherapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        //openWeatherMap
        const val API_KEY = "09c64b171e40b6f62e3af0b20c9fff56"
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    //位置情報取得用プロパティ
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    // 日付取得用プロパティ
    private val formatter = DateTimeFormatter.ofPattern("MM/dd")
    private var oneWeekStr = arrayOfNulls<String>(5)
    private lateinit var getDate: LocalDateTime
    private lateinit var getDateResult: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        //現在の日付を含めた5日分のarray
        for (i in 0..4) {
            oneWeekStr[i] = getOneWeek(i)
        }

        binding.apply {

            viewPagerLayout.adapter = WeatherListAdapter(this@HomeFragment)
            swipeLayout.setOnRefreshListener {
                //スワイプ(reload)した際の更新処理
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val gml = getMyLocation(location)
                        getMyLocationWeather(gml.first, gml.second)

                        swipeLayout.isRefreshing = false
                    } else {
                        Toast.makeText(requireContext(), "位置情報を取得できませんでした", Toast.LENGTH_LONG).show()
                        swipeLayout.isRefreshing = false
                    }
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPagerLayout) { tab, position ->
            tab.text = oneWeekStr[position]
        }.attach()

        //権限が付与されているかチェック
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) return
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //一週間分の日付文字を取得
    private fun getOneWeek(i: Int): String {
        getDate = LocalDateTime.now()
        getDateResult = getDate.plusDays(i.toLong()).format(formatter)
        return getDateResult
    }

    //場所を取得(緯度、経度)
    private fun getMyLocation(location: Location): Pair<Double, Double> {
        latitude = location.latitude
        longitude = location.longitude
        return Pair(latitude, longitude)
    }

    //取得した緯度と経度、API_KEYを埋め込み文字列を作成
    private fun getMyLocationWeather(latitude: Double, longitude: Double) {
        val siteUrl = "https://api.openweathermap.org/data/2.5/forecast?lang=ja"
        val weatherStr = "$siteUrl&lat=$latitude&lon=$longitude&units=metric&appid=$API_KEY"
        weatherTask(weatherStr)
    }

    /*
    ワーカースレッド処理
    getMyLocationWeatherで取得した文字列をURL化
     */
    private fun weatherTask(weatherStr: String) {
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                var httpResult = ""
                try {
                    val urlObj = URL(weatherStr)
                    val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                    httpResult = br.readText()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                return@withContext httpResult
            }
            weatherJsonTask(result)
        }
    }

    //取得したJSONデータを各fragmentへ渡す
    private fun weatherJsonTask(result: String) {
        sharedViewModel.data.value = result
    }

}