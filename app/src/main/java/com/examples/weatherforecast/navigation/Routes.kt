package com.examples.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.examples.weatherforecast.MainViewModel
import com.examples.weatherforecast.components.MainScreen
import com.examples.weatherforecast.components.SplashScreen

object Routes {
    const val SPLASH_SCREEN = "splash"
    const val MAIN_SCREEN = "main"
    const val ABOUT_SCREEN = "about"
    const val FAVORITE_SCREEN = "favorite"
    const val SEARCH_SCREEN = "search"
    const val SETTINGS_SCREEN = "settings"
}

// Composable defining the routes for all the composable screens.
@Composable
fun AppNavigation() {
    val navigationController = rememberNavController()

    // Here define all the route details.
    // Provide the nav controller and Start Destination to the NavHost.
    NavHost(navController = navigationController,
        startDestination = Routes.SPLASH_SCREEN) {
        // Inside this function, provide the route to class mapping details.
        composable(Routes.SPLASH_SCREEN){
            SplashScreen(navigationController)
        }
        composable(Routes.MAIN_SCREEN){
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navigationController, mainViewModel)
        }
    }
}