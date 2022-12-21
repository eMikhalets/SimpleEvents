package com.emikhalets.simpleevents.presentation.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.simpleevents.presentation.screens.add_event.AddEventScreen
import com.emikhalets.simpleevents.presentation.screens.edit_event.EditEventScreen
import com.emikhalets.simpleevents.presentation.screens.event_item.EventItemScreen
import com.emikhalets.simpleevents.presentation.screens.home.HomeScreen
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsScreen

private const val ARGS_EVENT_ID = "event_id"

@Composable
fun AppNavHost(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    NavHost(navController, AppScreen.Home.route) {
        composable(AppScreen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                onEventClick = { eventId ->
                    navController.navigate("${AppScreen.Event.route}/$eventId")
                }
            )
        }

        composable(AppScreen.AddEvent.route) {
            AddEventScreen(
                viewModel = hiltViewModel(),
                scaffoldState = scaffoldState,
                onEventAdded = { eventId ->
                    navController.navigate("${AppScreen.Event.route}/$eventId") {
                        popUpTo(AppScreen.Home.route)
                    }
                }
            )
        }

        composable(AppScreen.Settings.route) {
            SettingsScreen(
                viewModel = hiltViewModel(),
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
                    onEventDeleted = {
                        navController.popBackStack()
                    },
                    onEventEditClick = { eventId ->
                        navController.navigate("${AppScreen.EditEvent.route}/$eventId")
                    }
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
                    viewModel = hiltViewModel()
                )
            }
        }
    }
}
