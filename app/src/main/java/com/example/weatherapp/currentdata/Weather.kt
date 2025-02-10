package com.example.weatherapp.currentdata

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)