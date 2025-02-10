package com.example.weatherapp.model

data class WeatherModel(
    val city: String,          // The name of the city
    val date: String,          // The date (e.g., "2025-01-27")
    val temperature: Double,   // Current temperature (e.g., 22.5)
    val maxTemp: Double,       // Maximum temperature for the day (e.g., 25.0)
    val minTemp: Double,       // Minimum temperature for the day (e.g., 18.0)
    val iconRes: Int,          // Icon resource ID (e.g., R.drawable.sunny)
    val weatherStatus: String, // General weather status (e.g., "Clear", "Rainy")
    val humidity: Int?,        // Optional humidity value (e.g., 60 for 60%)
    val windSpeed: Double?,    // Optional wind speed value (e.g., 15.5 for 15.5 km/h)
    val pressure: Double?      // Optional atmospheric pressure value (e.g., 1012.5 for 1012.5 hPa)

)