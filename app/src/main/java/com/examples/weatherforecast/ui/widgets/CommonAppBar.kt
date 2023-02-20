package com.examples.weatherforecast.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CommonAppBar(
    title: String, icon: ImageVector? = null, isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    TopAppBar(title = {
     Text(text = title,
     color = MaterialTheme.colors.onSecondary,
     style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold))
    }, actions = {
                 if(isMainScreen){
                     IconButton(onClick = { navController.popBackStack() }) {
                         Icon(imageVector = Icons.Default.Search,
                             contentDescription = "Search")
                     }
                     IconButton(onClick = { navController.popBackStack() }) {
                         Icon(imageVector = Icons.Default.MoreVert,
                             contentDescription = "More Icon")
                     }
                 } else {

                 }
    }, navigationIcon = {
                        icon?.let {
                            Icon(imageVector = icon,
                                contentDescription = "back button", tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.clickable {
                                onButtonClicked.invoke()
                            })
                        }
    },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )

}