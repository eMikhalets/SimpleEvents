package com.emikhalets.simpleevents.ui.screens.common

import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.simpleevents.ui.screens.home.HomeScreen

sealed class AppScreen(val route: String, val icon: ImageVector) {
    object Home : AppScreen("home", Icons.Default.Home)
    object Add : AppScreen("add", Icons.Default.AddCircle)
    object Settings : AppScreen("settings", Icons.Default.Settings)
}

@Composable
fun SimpleEventsNavHost(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    NavHost(navController, AppScreen.Home.route) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                scaffoldState = scaffoldState
            )
        }
        composable(AppScreen.Add.route) {
//            SearchScreen(
//                scaffoldState = scaffoldState,
//            )
        }
        composable(AppScreen.Settings.route) {
//            SettingsScreen()
        }
    }
}