package com.emikhalets.simpleevents.ui.screens.common

import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
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

private const val ARGS_EVENT_ID = "app_args_event_id"

sealed class AppScreen(val route: String, val icon: ImageVector) {
    object Home : AppScreen("home", Icons.Default.Home)
    object AddEvent : AppScreen("add_event", Icons.Default.AddCircle)
    object Settings : AppScreen("settings", Icons.Default.Settings)
    object Event : AppScreen("event", Icons.Default.Build)
    object EditEvent : AppScreen("edit_event", Icons.Default.Edit)
}

@Composable
fun SimpleEventsNavHost(
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
        }
        composable(
            route = "${AppScreen.Event.route}/$ARGS_EVENT_ID",
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
            route = "${AppScreen.EditEvent.route}/$ARGS_EVENT_ID",
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

fun NavHostController.navToEvent(id: Long) {
    navigate("${AppScreen.Event.route}/$id")
}

fun NavHostController.navToEventAfterAdding(id: Long) {
    navigate("${AppScreen.Event.route}/$id") {
        popUpTo(AppScreen.Home.route)
    }
}

fun NavHostController.navToEditEvent(id: Long) {
    navigate("${AppScreen.EditEvent.route}/$id")
}