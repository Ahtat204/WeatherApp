import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.api.ApiKey
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.CurrentWeatherState
import com.example.weatherapp.api.ForeCastState
import com.example.weatherapp.forecastdata.ForecastResponse
import com.example.weatherapp.forecastdata.ForecastWeather
import com.example.weatherapp.forecastdata.Item0
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<CurrentWeatherState>()
    val weatherData: LiveData<CurrentWeatherState> get() = _weatherData

    private val _forecastData = MutableLiveData<ForeCastState>()
    val forecastData: LiveData<ForeCastState> get() = _forecastData

    private val _todayForecast = MutableLiveData<List<Item0>>()  // Filtered for LazyRow
    val todayForecast: LiveData<List<Item0>> get() = _todayForecast

    private val _groupedForecast = MutableLiveData<List<Item0>>()  // For LazyColumn
    val groupedForecast: LiveData<List<Item0>> get() = _groupedForecast


    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchForecastData(city: String, apiKey: String) {
        _forecastData.value = ForeCastState.Loading
        viewModelScope.launch{

            try {
                val response = weatherRepository.getForecast(city, apiKey)
                if(response.isSuccessful){
                  response.body()?.let{forecastresponse->
                      _forecastData.postValue(ForeCastState.Sucess(forecastresponse))
                      Log.d("WeatherViewModel", response.body().toString())
                      val forecastList = forecastresponse.list
                      processForecastData(forecastList)

                  }
                }
else{
    _forecastData.postValue(ForeCastState.Error("Error fetching forecast data"))
                    Log.e("WeatherViewModel", "Error fetching forecast data: ${response.message()}")
}
            }
            catch (e: Exception) {
                _forecastData.postValue(ForeCastState.Error("Exception occurred: ${e.message}"))
                Log.e("WeatherViewModel", "Exception occurred: ${e.message}", e)
            }

        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
        fun processForecastData(forecastList: List<Item0>) {
            // First 8 items for LazyRow
            val lazyRowForecastList = forecastList.take(8)
            _todayForecast.postValue(lazyRowForecastList)

            // Group forecast data by date and take the first forecast for each date (for LazyColumn)
            val groupedForecastList = forecastList
                .groupBy {
                    LocalDateTime.ofEpochSecond(it.dt.toLong(), 0, ZoneOffset.UTC).toLocalDate()
                }
                .mapValues { it.value.first() } // Pick one forecast per date (first in each group)

            _groupedForecast.postValue(groupedForecastList.values.toList())
        }
    fun fetchWeatherData(city: String) {
        _weatherData.value = CurrentWeatherState.Loading
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeatherData(city, apiKey = ApiKey.apikey)
                if (response.isSuccessful)
                {
                    _weatherData.postValue(CurrentWeatherState.Success(response.body()!!))
                    Log.d("WeatherViewModel", "Weather data fetched successfully")
                }
                else
                {
                    _weatherData.value = CurrentWeatherState.Error("Error fetching weather data")
                    Log.e("WeatherViewModel", "Error fetching weather data: ${response.message()}")
                }
            }
            catch
                (e: Exception) {
                _weatherData.value = CurrentWeatherState.Error("Exception occurred: ${e.message}")
                Log.e("WeatherViewModel", "Exception occurred: ${e.message}", e)
            }
        }
    }
}

