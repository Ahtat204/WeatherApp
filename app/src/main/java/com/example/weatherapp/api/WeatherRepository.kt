    package com.example.weatherapp.api

    import com.example.weatherapp.currentdata.WeatherData
    import com.example.weatherapp.forecastdata.ForecastResponse
    import com.example.weatherapp.forecastdata.ForecastWeather
    import retrofit2.Response

    open class WeatherRepository {
        open suspend fun getWeatherData(city: String, apiKey: String): Response<WeatherData> {
            return RetrofitInstance.apiService.getWeatherData(city, apiKey)

        }
        open suspend fun getForecast(city: String, apiKey: String): Response<ForecastWeather> {
            return RetrofitInstance.apiService.getHourlyForecast(city, apiKey)
        }
    }