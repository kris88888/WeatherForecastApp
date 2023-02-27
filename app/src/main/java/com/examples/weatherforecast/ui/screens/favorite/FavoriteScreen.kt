package com.examples.weatherforecast.ui.screens.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.examples.weatherforecast.R
import com.examples.weatherforecast.data.database.Favorite
import com.examples.weatherforecast.navigation.Routes
import com.examples.weatherforecast.ui.theme.AppTheme
import com.examples.weatherforecast.ui.widgets.CommonAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(id = R.string.favorite_screen_title),
                navController = navController,
                isMainScreen = false,
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(AppTheme.dimens.dimen4)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = viewModel.favList.collectAsState().value
                LazyColumn {

                    items(items = list) {
                        FavoriteCityRow(it, onCityRowClicked = { city ->
                            navController.navigate(Routes.MAIN_SCREEN+"/${city}")
                        }) {
                            viewModel.deleteFavorite(it.city, it.country)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun FavoriteCityRow(favorite: Favorite, onCityRowClicked: (String) -> Unit, onDeleteClicked: (favorite: Favorite) -> Unit) {
    Surface(
        Modifier
            .padding(AppTheme.dimens.dimen2)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                       onCityRowClicked(favorite.city)
            },
        shape = CircleShape.copy(CornerSize(6.dp)),
        color = MaterialTheme.colors.secondary
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city, modifier = Modifier.padding(AppTheme.dimens.dimen4))
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = "${favorite.country}",
                    modifier = Modifier.padding(AppTheme.dimens.dimen4),
                    style = AppTheme.typography.caption
                )
            }
            Icon(imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete ${favorite.city} from favorites",
                modifier = Modifier.clickable {
                    onDeleteClicked(favorite)
                })
        }
    }
}
