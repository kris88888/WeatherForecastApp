package com.examples.weatherforecast.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.examples.weatherforecast.ui.screens.main.MainViewModel
import com.examples.weatherforecast.R
import com.examples.weatherforecast.data.model.WeatherInformation
import com.examples.weatherforecast.data.model.WeatherResponse
import com.examples.weatherforecast.navigation.Routes
import com.examples.weatherforecast.network.ApiResult
import com.examples.weatherforecast.ui.screens.favorite.FavoriteScreenViewModel
import com.examples.weatherforecast.ui.screens.settings.SettingsViewModel
import com.examples.weatherforecast.ui.theme.AppTheme
import com.examples.weatherforecast.ui.widgets.CommonAppBar
import com.examples.weatherforecast.ui.widgets.ShowToast
import com.examples.weatherforecast.utils.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    favoriteViewModel: FavoriteScreenViewModel,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {

    city?.let {
        mainViewModel.city = it
    }
    val unitsFromDb = settingsViewModel.metricList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }

    val showDialog = rememberSaveable {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        SettingsDropDown(showDialog, navController)
    }
    // units can be either imperial or metric.
    if(!unitsFromDb.isNullOrEmpty()) {
        unit = unitsFromDb[0].unit
    }

    val weatherData = produceState<ApiResult<WeatherResponse, Boolean, Exception>>(
        initialValue = ApiResult(loading = true)
    ) {
        value = mainViewModel.getWeatherData(unit)
    }.value
    if (weatherData.loading) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        Log.d("KRIS", "$weatherData.data")
        MainScaffold(
            weatherData = weatherData.data!!,
            navController,
            mainViewModel.city,
            favoriteViewModel,
            showDialog
        )
    }
}

@Composable
fun SettingsDropDown(expanded: MutableState<Boolean>, navController: NavController) {
    val menuItems = stringArrayResource(id = R.array.menu_items)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value, onDismissRequest = {
                expanded.value = false
            }, modifier = Modifier
                .wrapContentWidth()
                .background(Color.White)
        ) {
            menuItems.forEachIndexed { index, text ->

                val clickListener = {
                    expanded.value = false
                    printMe(" $text clicked")

                }
                DropdownMenuItem(onClick = clickListener) {
                    val img = when (text) {
                        stringResource(id = R.string.about) -> Icons.Default.Info
                        stringResource(id = R.string.favorite) -> Icons.Default.Favorite
                        stringResource(id = R.string.settings) -> Icons.Default.Settings
                        else -> Icons.Default.Warning
                    }

                    val destination = when (text) {
                        stringResource(id = R.string.about) -> Routes.ABOUT_SCREEN
                        stringResource(id = R.string.favorite) -> Routes.FAVORITE_SCREEN
                        stringResource(id = R.string.settings) -> Routes.SETTINGS_SCREEN
                        else -> Routes.ABOUT_SCREEN
                    }
                    Icon(
                        imageVector = img,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = text,
                        style = TextStyle(color = AppTheme.colors.menuItemColor),
                        modifier = Modifier.clickable {
                            clickListener()
                            navController.navigate(destination)
                        },
                        fontWeight = FontWeight.W400
                    )
                }
            }
        }
    }
}

fun printMe(message: String) {
    Log.d("KRIS", message)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    weatherData: WeatherResponse,
    navController: NavController,
    city: String,
    favoriteViewModel: FavoriteScreenViewModel,
    showDialog: MutableState<Boolean>
) {
    val show = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Scaffold(topBar = {
        CommonAppBar(
            title = "${weatherData.city.name}, ${weatherData.city.country}",
            navController = navController,
            elevation = 5.dp,
            showFavoriteIcon = favoriteViewModel.isFavorite(city),
            onSearchClicked = {
                navController.navigate("${Routes.SEARCH_SCREEN}/$city")
            },
            onMenuItemClicked = {
                showDialog.value = !showDialog.value
            },
            onFavoriteClicked = {
                favoriteViewModel.addFavorite(weatherData.city.name, weatherData.city.country)
                show.value = true
            }
        )
        if(show.value){
            ShowToast(context = context, message = "Favorite Saved", show = show)
        }
    }) {

        MainContent(weatherData)

    }
}

@Composable
fun MainContent(weatherData: WeatherResponse) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val currentDayWeather = weatherData.list[0]
        Text(
            text = formatDate(currentDayWeather.dt),
            style = AppTheme.typography.caption,
            modifier = Modifier.padding(AppTheme.dimens.dimen6)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape
        ) {
            Column(
                modifier = Modifier.background(AppTheme.colors.yellow),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val url = Constants.GENERIC_IMAGE_URL.format(currentDayWeather.weather.get(0).icon)
                WeatherStateIcon(url)
                Text(
                    text = formatDecimals(currentDayWeather.temp.day) + Constants.DEGREE_SYMBOL,
                    style = AppTheme.typography.h4
                )
                Text(text = currentDayWeather.weather[0].main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindRow(
            humidity = "${currentDayWeather.humidity}%",
            pressure = "${currentDayWeather.pressure} psi",
            windspeed = "${currentDayWeather.speed} mph"
        )
        Divider()
        SunriseSunsetInfo(
            sunrise = formatToHourMinutes(currentDayWeather.sunrise.toLong()),
            sunset =
            formatToHourMinutes(currentDayWeather.sunset.toLong())
        )
        Text(
            text = stringResource(id = R.string.this_week),
            modifier = Modifier.padding(
                top = AppTheme.dimens.dimen24,
                bottom = AppTheme.dimens.dimen4
            ),
            fontWeight = FontWeight.Bold,
            style = AppTheme.typography.subtitle1
        )
        Divider()
        NextWeekWeather(weatherData.list)
    }
}

@Composable
fun NextWeekWeather(list: List<WeatherInformation>) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), color = AppTheme.colors.greyBackground,
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(AppTheme.dimens.dimen2),
            contentPadding = PaddingValues(1.dp)
        ) {
            items(list) { item: WeatherInformation ->
                WeekdayDayWeather(item)
            }
        }
    }
}

@Composable
fun WeekdayDayWeather(item: WeatherInformation) {
    val imageUrl = Constants.GENERIC_IMAGE_URL.format(item.weather[0].icon)
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(AppTheme.dimens.dimen12),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val date = "${getDayOfWeek(item.dt)}\n${getMonthDate(item.dt)}"
            Text(text = date, modifier = Modifier.padding(AppTheme.dimens.dimen2))
            WeatherStateIcon(url = imageUrl)
            CircularDescription(text = item.weather[0].description)
            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = AppTheme.colors.Blue,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(Math.round(item.temp.max).toString() + Constants.DEGREE_SYMBOL)
                    }
                })

                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = AppTheme.colors.DarkGrey,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append(Math.round(item.temp.min).toString() + Constants.DEGREE_SYMBOL)
                    }
                }, modifier = Modifier.padding(start = AppTheme.dimens.dimen2))
            }
        }
    }
}

@Composable
fun CircularDescription(text: String) {
    Surface(
        shape = CircleShape,
        color = AppTheme.colors.yellow
    ) {
        Text(
            text = text, modifier = Modifier.padding(10.dp),
            style = AppTheme.typography.caption
        )
    }
}

@Composable
fun HumidityWindRow(humidity: String, pressure: String, windspeed: String) {
    Row(
        modifier = Modifier
            .padding(AppTheme.dimens.dimen12)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Row(modifier = Modifier.padding(AppTheme.dimens.dimen4)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = AppTheme.dimens.dimen4)
            )
            Text(text = "$humidity", style = AppTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(AppTheme.dimens.dimen4)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "air pressure",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = AppTheme.dimens.dimen4)
            )
            Text(text = "$pressure", style = AppTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(AppTheme.dimens.dimen4)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind speed",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = AppTheme.dimens.dimen4)
            )
            Text(
                text = "$windspeed",
                style = AppTheme.typography.caption
            )
        }

    }
}

@Composable
fun SunriseSunsetInfo(sunrise: String, sunset: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = AppTheme.dimens.dimen15,
                bottom = AppTheme.dimens.dimen6
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(AppTheme.dimens.dimen4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise time",
                modifier = Modifier
                    .size(AppTheme.dimens.dimen30)
                    .padding(end = AppTheme.dimens.dimen4),
            )
            Text(
                text = "$sunrise",
                style = AppTheme.typography.caption
            )
        }
        Row(
            modifier = Modifier.padding(AppTheme.dimens.dimen4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset time",
                modifier = Modifier
                    .size(AppTheme.dimens.dimen35)
                    .padding(end = AppTheme.dimens.dimen4)
            )
            Text(
                text = "$sunset",
                style = AppTheme.typography.caption
            )
        }
    }
}

@Composable
fun WeatherStateIcon(url: String) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = Modifier.size(AppTheme.dimens.dimen80)
    )
}


@Preview
@Composable
fun PreviewMainContent() {
    //;;;;;;;;MainContent()
}