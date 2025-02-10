package com.example.weatherapp

import WeatherViewModel
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.api.ApiKey
import com.example.weatherapp.screens.MainScreen
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.ui.theme.pangolineregular

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(scrim = 0xFFF5F5F5.toInt(), darkScrim = 0xFF4B2424.toInt())
        )

        val weatherRepository = WeatherRepository()

        // ✅ Correctly instantiate ViewModel with Factory
        val weatherViewModel: WeatherViewModel by viewModels {
            WeatherViewModelFactory(weatherRepository)
        }
        weatherViewModel.fetchForecastData("Agadir", ApiKey.apikey)

        setContent {
            MainScreen(weatherViewModel) // Pass ViewModel to UI
        }
    }
}

@Preview
@Composable
private fun Test() {

    Surface() {
        Column(
            modifier = Modifier
                .width(300.dp).height(90.dp) // Adjust the width as per requirement
            , // Add padding to column
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sun",
                modifier = Modifier
                    .padding(50.dp).size(150.dp, 150.dp)
                    .offset(x = 10.dp, y = 70.dp),
                alignment = Alignment.Center
            )
            Text(
                text = "weatherStatus.value",
                modifier = Modifier
                    .size(120.dp).wrapContentSize()
                    .offset(x = 8.dp, y = 20.dp),
                color = Color.Black,
                fontSize = 30.sp,
                fontFamily = pangolineregular,
                textAlign = TextAlign.Justify
            )
            Text(
                text = "Currenttemperature.value",
                fontSize = 25.sp,
                color = Color.Black,
                fontFamily = pangolineregular,
                modifier = Modifier.offset(x = 10.dp, y = 60.dp)
            )
        }

    }
}