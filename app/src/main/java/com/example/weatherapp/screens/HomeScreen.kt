package com.example.weatherapp.screens

import WeatherViewModel
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.CurrentWeatherState
import com.example.weatherapp.currentdata.Clouds
import com.example.weatherapp.currentdata.Coord
import com.example.weatherapp.currentdata.WeatherData
import com.example.weatherapp.model.WeatherModel
import com.example.weatherapp.ui.theme.BottomSheetBackground
import com.example.weatherapp.ui.theme.Offwhite
import com.example.weatherapp.ui.theme.merriweathernold
import com.example.weatherapp.ui.theme.merriweatherregular
import com.example.weatherapp.ui.theme.pangolineregular
import com.example.weatherapp.ui.theme.poppinsbold

import retrofit2.Response
import java.time.LocalDate

val FontSize = 30
val BottomBoxFontSize = 18.5

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainScreen(weatherViewModel: WeatherViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    val windspeed = remember { mutableStateOf("") }
    val Ultaviolet = remember { mutableStateOf("") }
    val humidity = remember { mutableStateOf("") }
    val Pressure = remember { mutableStateOf("") }
    val Currenttemperature = remember { mutableStateOf("30") }
    val weatherStatus = remember { mutableStateOf("Clear sky") }
    val city = remember { mutableStateOf("Agadir") }
    val date :LocalDate = LocalDate.now()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current.density
    val collapsedHeight = 270.dp
    val expandedHeight = screenHeight - collapsedHeight
    var sheetoffset by remember { mutableStateOf(screenHeight - collapsedHeight) }
    val weatherData by weatherViewModel.weatherData.observeAsState()
    val dragState = rememberDraggableState { delta ->
        sheetoffset = (sheetoffset + delta.dp).coerceIn(
            screenHeight - expandedHeight, screenHeight - collapsedHeight
        )
    }

    val days = listOf(
        WeatherModel(
            city.value, "2025-01-28", 22.5, 25.0, 18.0, R.drawable.sun, "Clear", 60, 15.5, 1012.5
        ),
        WeatherModel(
            city.value, "2025-01-29", 22.5, 25.0, 18.0, R.drawable.sun, "Clear", 60, 15.5, 1012.5
        ),
        WeatherModel(
            city.value, "2025-01-30", 22.5, 25.0, 18.0, R.drawable.sun, "Clear", 60, 15.5, 1012.5
        ),
        WeatherModel(
            city.value, "2025-01-30", 22.5, 25.0, 18.0, R.drawable.sun, "Clear", 60, 15.5, 1012.5
        ),

        )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clip(RoundedCornerShape(40.dp))
    ) {
        Box(
            modifier = Modifier
                .offset(x = 4.dp, y = 27.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(Offwhite)
                .padding(10.dp), contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = city.value,
                modifier = Modifier.padding(10.dp),
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = merriweathernold,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = date.toString(),
                modifier = Modifier.padding(vertical = 40.dp),
                color = Color.Black
            )
            IconButton(
                onClick = {showDialog.value = true}, modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            if (showDialog.value) {
                CitySearchPopup(
                    cityState = city, // Pass the city state to the popup
                    showDialog = showDialog,
                    onSearch = { newCity ->  // Correct lambda function syntax
                        weatherViewModel.fetchWeatherData(newCity) // Directly fetch with newCity
                    }
                )
            }


        }



        when (val state = weatherData){
            is CurrentWeatherState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { CircularProgressIndicator()
                }
            }

            is CurrentWeatherState.Error -> {
            }
            is CurrentWeatherState.Success -> {
                val Data = state.data
                windspeed.value = Data.wind.speed.toString()
                Ultaviolet.value = Data.main.humidity.toString()
                Currenttemperature.value = Data.main.temp + "°C"
                weatherStatus.value = Data.weather.firstOrNull()?.description ?: "No description available"
                humidity.value = Data.main.humidity.toString()
                Pressure.value = Data.main.pressure
                val iconCode by remember { mutableStateOf(Data.weather.firstOrNull()?.icon ?: "") }
                val iconRes = getWeatherIconR(iconCode)
                Log.d("the icon is",iconCode)



                Column(
                    modifier = Modifier
                        .width(30.dp)
                        .height(90.dp), // Adjust the width as per requirement
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Image Loading with Async Image Painter
                    val painter =
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = "Weather Icon",
                        modifier = Modifier
                            .padding(50.dp)
                            .size(120.dp)
                            .offset(x = 10.dp, y = 70.dp),
                        alignment = Alignment.Center
                    )

                    Text(
                        text = weatherStatus.value,
                        modifier = Modifier
                            .size(120.dp)
                            .wrapContentSize()
                            .offset(x = 8.dp, y = 20.dp),
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontFamily = pangolineregular,
                        textAlign = TextAlign.Justify
                    )
                    Text(
                        text = Currenttemperature.value,
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontFamily = pangolineregular,
                        modifier = Modifier.offset(x = 10.dp, y = 60.dp)
                    )
                }



            }

            null ->{

            }

        }

        // Column modifier fix

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight)
            .offset { IntOffset(0, sheetoffset.roundToPx()) }
            .background(BottomSheetBackground, RoundedCornerShape(45.dp))
            .draggable(state = dragState,
                orientation = Orientation.Vertical,
                onDragStarted = {},
                onDragStopped = {})) {
            Column(
                modifier = Modifier.padding(vertical = 35.dp, horizontal = 20.dp)
            ) {
                Text(
                    "Today",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = poppinsbold,
                    fontSize = BottomBoxFontSize.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            val hours = ThreeHoursView()

            val temperature = listOf(10f,12f,14f,16f, 18f, 20f, 22f, 24f
            )
            HourlyWeatherView(
                hours = hours,
                temperature = temperature,
                Icon = listOf(R.drawable.sun, R.drawable.sun, R.drawable.sun)
            )

            Column(Modifier.wrapContentHeight()) {
                Text(
                    "Next 4 Days",
                    modifier = Modifier
                        .padding(horizontal = 19.dp)
                        .offset(y = 200.dp),
                    fontFamily = poppinsbold,
                    fontSize = BottomBoxFontSize.sp
                )
                LazyColumn(
                    Modifier
                        .offset(y = 200.dp)
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(days) { day ->
                        FourDaysTamView(day)
                    }
                }
            }
            Text(
                "Details",
                modifier = Modifier
                    .offset(y = 470.dp)
                    .padding(horizontal = 23.dp),
                fontFamily = poppinsbold,
                fontSize = BottomBoxFontSize.sp
            )
            Text(
                "Humidity",
                modifier = Modifier
                    .offset(y = 510.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "${humidity.value}%",
                modifier = Modifier
                    .offset(x = 100.dp, y = 510.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "Wind Speed",
                modifier = Modifier
                    .offset(y = 560.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "${windspeed.value}km/h",
                modifier = Modifier
                    .offset(x = 110.dp, y = 560.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "Pressure",
                modifier = Modifier
                    .offset(x = 200.dp, y = 560.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "${Pressure.value}hPa",
                modifier = Modifier
                    .offset(x = 290.dp, y = 560.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "Ultraviolet",
                modifier = Modifier
                    .offset(x = 200.dp, y = 510.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
            Text(
                "${Ultaviolet.value}%",
                modifier = Modifier
                    .offset(x = 300.dp, y = 510.dp)
                    .padding(horizontal = 23.dp),
                fontSize = 18.sp
            )
        }
    }
}

// Define the plus operator for Dp + Float (in pixels)
@Composable
operator fun Dp.plus(delta: Float): Dp {
    val density = LocalDensity.current.density
    return (this.value + delta / density).dp
}
// this function is to generate  hour of the day per 3 hours
fun ThreeHoursView(): List<String> {
    val hours = mutableListOf<String>()
    for (i in 0 until 24 step 3) {
        val hourstring = String.format("%02d", i)
        hours.add(hourstring)
    }
    return hours
}
@Composable
fun HourlyWeatherView(hours: List<String>, temperature: List<Float>, Icon: List<Int>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(hours) { index, hour ->
            HourItem(hour = hour, temperature = temperature[index], icon = R.drawable.sun)
        }
    }
}
@Composable
fun FourDaysTamView(model: WeatherModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 10.dp, y = 20.dp)
            .padding(vertical = 5.dp)
    ) {
        Text(text = model.date, fontFamily = merriweatherregular)
        Spacer(modifier = Modifier.width(100.dp))
        Text(text = model.maxTemp.toString() + "°C", fontFamily = merriweatherregular)
        Text(
            text = model.minTemp.toString() + "°C",
            fontFamily = merriweatherregular,
            modifier = Modifier.offset(x = 40.dp)
        )
        Spacer(modifier = Modifier.width(60.dp))
        Image(
            alignment = Alignment.Center,
            painter = painterResource(id = model.iconRes),
            contentDescription = "weather icon",
            modifier = Modifier
                .size(40.dp)
                .offset(x = 0.dp, y = -15.dp)
        )
    }
}
//this funtion create a single item of the hours lazyrow
@Composable
fun HourItem(hour: String, temperature: Float, icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(text = hour, fontFamily = merriweatherregular)
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Text(text = "${temperature}°C", style = MaterialTheme.typography.bodyLarge)

    }

}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:width=411dp,height=891dp",
    backgroundColor = 0xFF4B2424
)
@Composable
fun HomeScreenPreview() {
    val mockweatherdata = object : WeatherRepository() {
        override suspend fun getWeatherData(city: String, apiKey: String): Response<WeatherData> {
            return Response.success(
                WeatherData(
                    "mock",
                    clouds = Clouds(20),
                    cod = 20,
                    coord = Coord(10.1, 30.2),
                    dt = TODO(),
                    id = TODO(),
                    main = TODO(),
                    name = TODO(),
                    sys = TODO(),
                    timezone = TODO(),
                    visibility = TODO(),
                    weather = TODO(),
                    wind = TODO(),
                )
            )
        }
    }
    val mockweatherviewmodel = WeatherViewModel(mockweatherdata)
    MainScreen(mockweatherviewmodel)
}


fun getWeatherIconR(iconCode: String): Int{
    return when (iconCode) {
        "01d", "01n" -> R.drawable.sun  // Clear sky for day and night
        "02d", "02n" -> R.drawable.clouds // Few clouds for day and night
        "03d", "03n" -> R.drawable.weather // Scattered clouds
        "04d", "04n" -> R.drawable.clouds // Broken clouds
        "09d", "09n" -> R.drawable.rainny // Shower rain
        "10d", "10n" -> R.drawable.rain // Rain
        "11d", "11n" -> R.drawable.thunderstorm // Thunderstorm
        "13d", "13n" -> R.drawable.snowflake // Snow
        "50d", "50n" -> R.drawable.mist // Mist or fog
        else -> R.drawable.sun // Default icon for unknown codes
    }
}





