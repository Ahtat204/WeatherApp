package com.example.weatherapp.forecastdata

data class ForecastWeather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Item0>,
    val message: Int
)