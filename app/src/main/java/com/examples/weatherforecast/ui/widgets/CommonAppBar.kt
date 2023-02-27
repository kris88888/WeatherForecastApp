package com.examples.weatherforecast.ui.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CommonAppBar(
    title: String,
    backArrowIcon: ImageVector = Icons.Default.ArrowBack,
    isMainScreen: Boolean = true,
    showFavoriteIcon: Boolean = false,
    elevation: Dp = 0.dp,
    navController: NavController,
    onFavoriteClicked: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onMenuItemClicked: () -> Unit = {},
) {
    val isFavorite = remember {
        mutableStateOf(showFavoriteIcon)
    }


    TopAppBar(title = {
        Text(
            text = title,
            color = MaterialTheme.colors.secondary,
            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = {
                onSearchClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(onClick = { onMenuItemClicked() }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Icon"
                )
            }
        }
    }, navigationIcon = {
        if (!isMainScreen) {

            Icon(imageVector = backArrowIcon,
                contentDescription = "back button",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                })
        }

        if (isMainScreen && isFavorite.value) {
            Icon(
                imageVector = Icons.Default.Favorite, contentDescription = "Favorite Icon",
                modifier = Modifier
                    .scale(0.9f)
                    .clickable {
                        onFavoriteClicked()
                    }, tint = Color.Red.copy(alpha = 0.6f)
            )
        }
    }, backgroundColor = MaterialTheme.colors.background,
        elevation = elevation
    )

}


@Composable
fun ShowToast(context: Context, message: String, show: MutableState<Boolean>) {
    if (show.value) {
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
    }
}