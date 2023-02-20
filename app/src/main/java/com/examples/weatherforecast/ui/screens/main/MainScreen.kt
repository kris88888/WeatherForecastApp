package com.examples.weatherforecast.components

import android.annotation.SuppressLint
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examples.weatherforecast.MainViewModel
import com.examples.weatherforecast.data.model.WeatherResponse
import com.examples.weatherforecast.network.ApiResult
import com.examples.weatherforecast.ui.widgets.CommonAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel) {
    val weatherData = produceState<ApiResult<WeatherResponse, Boolean, Exception>>(
        initialValue = ApiResult(loading = true)
    ) {
        value = mainViewModel.getWeatherData()
    }.value
    if (weatherData.loading) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData = weatherData.data!!, navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weatherData: WeatherResponse, navController: NavController) {
   Scaffold(topBar = {
       CommonAppBar(title = "${weatherData.city.name}, ${weatherData.city.country}",
           navController = navController,
           icon = Icons.Default.ArrowBack,
           onButtonClicked = {
                             navController.popBackStack()
           },
       elevation = 5.dp)
   }) { MainContent(weatherData) }
}

@Composable
fun MainContent(weatherData: WeatherResponse) {
    Text(text = "Main Screen ${weatherData.city}")
}