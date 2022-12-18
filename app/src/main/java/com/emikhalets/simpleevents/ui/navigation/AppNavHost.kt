package com.emikhalets.simpleevents.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.simpleevents.ui.screens.add_event.AddEventScreen
import com.emikhalets.simpleevents.ui.screens.edit_event.EditEventScreen
import com.emikhalets.simpleevents.ui.screens.event_item.EventItemScreen
import com.emikhalets.simpleevents.ui.screens.home.HomeScreen
import com.emikhalets.simpleevents.ui.screens.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    NavHost(navController, AppScreen.Home.route) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(AppScreen.AddEvent.route) {
            AddEventScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(
            route = "${AppScreen.Event.route}/{$ARGS_EVENT_ID}",
            arguments = listOf(navArgument(ARGS_EVENT_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(ARGS_EVENT_ID)?.let { id ->
                EventItemScreen(
                    eventId = id,
                    viewModel = hiltViewModel(),
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }
        }
        composable(
            route = "${AppScreen.EditEvent.route}/{$ARGS_EVENT_ID}",
            arguments = listOf(navArgument(ARGS_EVENT_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(ARGS_EVENT_ID)?.let { id ->
                EditEventScreen(
                    eventId = id,
                    viewModel = hiltViewModel(),
                    navController = navController,
                    scaffoldState = scaffoldState
                )
            }
        }
    }
}
