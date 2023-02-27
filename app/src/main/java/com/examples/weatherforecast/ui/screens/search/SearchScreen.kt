package com.examples.weatherforecast.ui.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.examples.weatherforecast.R
import com.examples.weatherforecast.navigation.Routes
import com.examples.weatherforecast.ui.theme.AppTheme
import com.examples.weatherforecast.ui.widgets.CommonAppBar

private const val TAG = "SearchScreen"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navigationController: NavHostController, defaultCity:String) {
    Scaffold(topBar = {
        CommonAppBar(
            title = defaultCity,
            navController = navigationController,
            isMainScreen = false,
            backArrowIcon = Icons.Default.ArrowBack
        )
    }) {
        Surface() {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.dimen16)
                        .align(Alignment.CenterHorizontally),
                    hint = stringResource(id = R.string.city_hint)
                ) { city ->
                    navigationController.navigate(Routes.MAIN_SCREEN+"/$city")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String,
    onSearchClicked: (String) -> Unit
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column() {
        CommonTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.dimen16)
                .align(Alignment.CenterHorizontally),
            valueState = searchQueryState,
            placeholder = "Burlington",
            keyboardActions = KeyboardActions {
                if (valid) {
                    onSearchClicked(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommonTextField(
    modifier:Modifier = Modifier,
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { str: String ->
            valueState.value = str
        },
        label = { Text(placeholder)},
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black
        ),
        keyboardActions = keyboardActions,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    )
}