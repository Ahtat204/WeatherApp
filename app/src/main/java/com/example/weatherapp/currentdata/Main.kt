package com.example.weatherapp.currentdata

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: String,
    val sea_level: Int,
    val temp: String,
    val temp_max: String,
    val temp_min: String
)