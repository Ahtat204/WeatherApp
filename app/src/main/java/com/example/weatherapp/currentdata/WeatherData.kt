package com.example.weatherapp.currentdata

data class WeatherData(
    val base: String?,
    val clouds: Clouds?,
    val cod: Int,
    val coord: Coord?,
    val dt: Int,
    val id: Int,
    val main: Main, //humidity pressure temp_min temp_max
    val name: String,
    val sys: Sys?,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>, //icon description(weather status)
    val wind: Wind
)