package com.example.weatherapp.api

import com.example.weatherapp.currentdata.WeatherData
import com.example.weatherapp.forecastdata.ForecastResponse
import com.example.weatherapp.forecastdata.ForecastWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Response<WeatherData>

    @GET("forecast")
    suspend fun getHourlyForecast(
        @Query("city")city: String,
        @Query("appid") apiKey: String
    ): Response<ForecastWeather>
}