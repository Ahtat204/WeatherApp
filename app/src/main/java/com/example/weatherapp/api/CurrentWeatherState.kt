package com.example.weatherapp.api

import com.example.weatherapp.currentdata.WeatherData

sealed class CurrentWeatherState{
    data class Success(val data: WeatherData) : CurrentWeatherState()
    data class Error(val message: String) : CurrentWeatherState()
    object Loading: CurrentWeatherState()
}