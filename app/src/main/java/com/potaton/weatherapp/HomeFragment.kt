package com.potaton.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import com.potaton.weatherapp.databinding.FragmentHomeBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //位置情報取得用プロパティ
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // 日付取得用プロパティ
    private val formatter = DateTimeFormatter.ofPattern("MM/dd")
    private var oneWeekStr = arrayOfNulls<String>(7)
    private lateinit var getDate: LocalDateTime
    private lateinit var getDateResult: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //現在の日付を含めた7日分のarray
        for (i in 0..6) {
            oneWeekStr[i] = getOneWeek(i)
        }

        binding.apply {
            viewPagerLayout.adapter = WeatherListAdapter(this@HomeFragment)
            swipeLayout.setOnRefreshListener {
                //スワイプした際の更新処理
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        val latitude = it.latitude
                        val longitude = it.longitude

                        swipeLayout.isRefreshing = false
                    }else{
                        Toast.makeText(requireContext(), "位置情報を取得できませんでした", Toast.LENGTH_LONG).show()
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
}

