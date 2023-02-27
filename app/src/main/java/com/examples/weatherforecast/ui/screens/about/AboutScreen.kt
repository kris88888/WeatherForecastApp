package com.examples.weatherforecast.ui.screens.about

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.examples.weatherforecast.R
import com.examples.weatherforecast.ui.theme.AppTheme
import com.examples.weatherforecast.ui.widgets.CommonAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(topBar = {
        CommonAppBar(
            title = stringResource(id = R.string.about),
            isMainScreen = false,
            navController = navController
        )
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.dimen12)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.about_text1),
                    style = AppTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.about_text2),
                    style = AppTheme.typography.subtitle1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                    text = "${stringResource(id = R.string.about_text3)}\n${stringResource(id = R.string.base_url)}"
                )
            }
        }
    }
}