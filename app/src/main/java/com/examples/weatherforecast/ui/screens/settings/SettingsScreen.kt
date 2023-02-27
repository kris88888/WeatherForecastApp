package com.examples.weatherforecast.ui.screens.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.weatherforecast.R
import com.examples.weatherforecast.data.database.Unit
import com.examples.weatherforecast.ui.widgets.CommonAppBar


private const val TAG = "SettingsScreen"

@Composable
fun getDisplayableText(text:String): String {
    return if(text == stringResource(id = R.string.fahrenheit)) {
        "Fahrenheit (F)"
    } else if(text == stringResource(id = R.string.Celsius)) {
        "Celsius (C)"
    } else  ""
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        CommonAppBar(
            title = stringResource(id = R.string.settings),
            isMainScreen = false,
            navController = navController
        )
    }) {
        var unitsToggleState by remember {
            mutableStateOf(false)
        }
        val measurementUnits = stringArrayResource(id = R.array.measurement_list)
        val choiceFromDb = remember {
            mutableStateOf(viewModel.metricList.value)
        }
        Log.d(TAG, "DB CHOICE = $choiceFromDb")

        val defaultChoice = remember {
            if (choiceFromDb.value.isEmpty()) {
                measurementUnits[0]
            } else {
                choiceFromDb.value[0].unit
            }
        }
        Log.d(TAG, "DEFAULT CHOICE = $defaultChoice")

        var choiceState by remember {
            mutableStateOf(defaultChoice)
        }
        val fahrenheit = stringResource(id = R.string.fahrenheit)
        val celsius = stringResource(id = R.string.Celsius)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.settings_screen_text),
                    modifier = Modifier.padding(15.dp)
                )
                IconToggleButton(
                    checked = !unitsToggleState,
                    onCheckedChange = {
                        unitsToggleState = !it
                        choiceState = if (choiceState == celsius) {
                            fahrenheit
                        } else {
                            celsius
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RectangleShape)
                        .padding(5.dp)
                        .background(MaterialTheme.colors.primary.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = getDisplayableText(text = choiceState)
                    )
                }

                Button(
                    onClick = {
                        viewModel.deleteAllUnits()
                        viewModel.addUnit(Unit(unit = choiceState, ""))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEFBE42))
                ) {
                    Text(text = stringResource(id = R.string.save), fontSize = 17.sp)
                }
            }
        }
    }
}