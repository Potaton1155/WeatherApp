package com.potaton.weatherapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@SerialName("WeatherData")
@Serializable
data class WeatherResponse(
    val list: List<WeatherEntry>,
    val city: City,
)

@SerialName("WeatherEntry")
@Serializable
data class WeatherEntry(
    val main: MainData,
    val weather: List<Weather>,
    val wind: Wind,
    val pop: Float,
    val sys: Sys,
    val dt_txt: String,
    //null許容にしておかないと、データ取得時にrain,snowフィールドがない場合、"MissingFieldException"でアプリがクラッシュする
    val rain: Rain? = null,
    val snow: Snow? = null,
)

@SerialName("MainData")
@Serializable
data class MainData(
    //気温
    val temp: Double,
    val temp_min: Float,
    val temp_max: Float,
    //湿度
    val humidity: Int,
)

@SerialName("Weather")
@Serializable
data class Weather(
    //天気詳細
    val description: String,
)

@SerialName("Wind")
@Serializable
data class Wind(
    //風速
    val speed: Float,
    //風向き
    val deg: Int,
)

@SerialName("Sys")
@Serializable
data class Sys(
    //データ取得時間
    val pod: String,
)

@SerialName("Rain")
@Serializable
data class Rain(
    //降水量
    val `3h`: Float? = 0.0F,
)

@SerialName("Snow")
@Serializable
data class Snow(
    //降雪量
    val `3h`: Float? = 0.0F,
)

@SerialName("City")
@Serializable
data class City(
    //都市名
    val name: String,
    //座標
    val coord: Coord,
    //日の出
    val sunrise: Long,
    //日没
    val sunset: Long,
)

@SerialName("Coord")
@Serializable
data class Coord(
    //緯度
    val lat: Double,
    //経度
    val lon: Double,
)