package com.potaton.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val weatherData: MutableLiveData<String> = MutableLiveData()
}