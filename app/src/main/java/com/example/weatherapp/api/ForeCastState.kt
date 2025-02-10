package com.example.weatherapp.api

import com.example.weatherapp.forecastdata.ForecastResponse
import com.example.weatherapp.forecastdata.ForecastWeather

sealed class ForeCastState {
    data class Sucess(val data: ForecastWeather) : ForeCastState()
    data class Error(val message: String) : ForeCastState()
    object Loading : ForeCastState()
}