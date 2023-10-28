package com.emikhalets.simpleevents.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.simpleevents.presentation.screens.add_event.AddEventScreen
import com.emikhalets.simpleevents.presentation.screens.edit_event.EditEventScreen
import com.emikhalets.simpleevents.presentation.screens.event_item.EventItemScreen
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListScreen
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListViewModel
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsScreen
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, AppRoute.EventsList) {

        composable(AppRoute.EventsList) {
            val viewModel: EventsListViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            EventsListScreen(
                state = state,
                onAction = viewModel::setAction,
                onEventClick = { navController.navigate(eventDetailsRoute(it)) }
            )
        }

        composable(eventDetailsRoute, listOf(eventIdArgument)) {
            EventItemScreen(
                eventId = it.getEventId(),
                viewModel = hiltViewModel(),
                onEventDeleted = { navController.popBackStack() },
                onEventEditClick = { id -> navController.navigate(eventEditRoute(id)) }
            )
        }

        composable(AppRoute.EventAdd) {
            AddEventScreen(
                viewModel = hiltViewModel(),
                onEventAdded = { id -> navController.navigate(eventDetailsRoute(id)) }
            )
        }

        composable(eventEditRoute, listOf(eventIdArgument)) {
            EditEventScreen(
                eventId = it.getEventId(),
                viewModel = hiltViewModel()
            )
        }

        composable(AppRoute.Settings) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            SettingsScreen(
                state = state,
                onAction = viewModel::setAction,
            )
        }
    }
}
