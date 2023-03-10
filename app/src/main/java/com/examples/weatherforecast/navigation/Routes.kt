package com.examples.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.examples.weatherforecast.ui.screens.main.MainViewModel
import com.examples.weatherforecast.components.MainScreen
import com.examples.weatherforecast.components.SplashScreen
import com.examples.weatherforecast.ui.screens.about.AboutScreen
import com.examples.weatherforecast.ui.screens.favorite.FavoriteScreen
import com.examples.weatherforecast.ui.screens.favorite.FavoriteScreenViewModel
import com.examples.weatherforecast.ui.screens.search.SearchScreen
import com.examples.weatherforecast.ui.screens.settings.SettingsScreen

object Routes {
    const val SPLASH_SCREEN = "splash"
    const val MAIN_SCREEN = "main"
    const val ABOUT_SCREEN = "about"
    const val FAVORITE_SCREEN = "favorite"
    const val SEARCH_SCREEN = "search"
    const val SETTINGS_SCREEN = "settings"
}

object RoutePathArgs {
    const val CITY = "city"
}

// Composable defining the routes for all the composable screens.
@Composable
fun AppNavigation() {
    val navigationController = rememberNavController()

    // Here define all the route details.
    // Provide the nav controller and Start Destination to the NavHost.
    NavHost(
        navController = navigationController,
        startDestination = Routes.SPLASH_SCREEN
    ) {
        // Inside this function, provide the route to class mapping details.
        composable(Routes.SPLASH_SCREEN) {
            SplashScreen(navigationController)
        }
        composable("${Routes.MAIN_SCREEN}/{${RoutePathArgs.CITY}}",
            arguments = listOf(
                navArgument("city") {
                    type = NavType.StringType
                }
            )) {
            val city = it.arguments?.getString(RoutePathArgs.CITY)
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(
                navController = navigationController,
                mainViewModel,
                hiltViewModel<FavoriteScreenViewModel>(),
                city = city
            )
        }
        composable("${Routes.SEARCH_SCREEN}/{${RoutePathArgs.CITY}}", arguments = listOf(
            navArgument("city") {
                type = NavType.StringType
            }
        )) {
            val city = it.arguments?.getString(RoutePathArgs.CITY)
            city?.let {
                SearchScreen(navigationController, city)
            } ?: kotlin.run {
                throw java.lang.IllegalArgumentException("Mandatory Argument Missing: City Name")
            }

        }
        composable(Routes.ABOUT_SCREEN) {
            AboutScreen(navigationController)
        }

        composable(Routes.FAVORITE_SCREEN) {
            FavoriteScreen(navigationController)
        }
        composable(Routes.SETTINGS_SCREEN) {
            SettingsScreen(navigationController)
        }
    }
}