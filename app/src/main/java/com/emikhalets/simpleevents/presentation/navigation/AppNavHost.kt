package com.emikhalets.simpleevents.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emikhalets.simpleevents.presentation.screens.add_event.AddEventScreen
import com.emikhalets.simpleevents.presentation.screens.edit_event.EditEventScreen
import com.emikhalets.simpleevents.presentation.screens.event_item.EventItemScreen
import com.emikhalets.simpleevents.presentation.screens.events_calendar.EventsCalendarScreen
import com.emikhalets.simpleevents.presentation.screens.events_calendar.EventsCalendarViewModel
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListScreen
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListViewModel
import com.emikhalets.simpleevents.presentation.screens.group_edit.GroupEditScreen
import com.emikhalets.simpleevents.presentation.screens.group_edit.GroupEditViewModel
import com.emikhalets.simpleevents.presentation.screens.group_item.GroupItemScreen
import com.emikhalets.simpleevents.presentation.screens.group_item.GroupItemViewModel
import com.emikhalets.simpleevents.presentation.screens.groups.GroupsScreen
import com.emikhalets.simpleevents.presentation.screens.groups.GroupsViewModel
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsScreen
import com.emikhalets.simpleevents.presentation.screens.settings.SettingsViewModel

private const val ARGS_EVENT_ID = "event_id"
private const val ARGS_GROUP_ID = "group_id"

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, AppScreen.EventsList.route) {

        composable(AppScreen.EventsList.route) {
            val viewModel: EventsListViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            EventsListScreen(
                state = state,
                onAction = viewModel::setAction,
                onEventClick = { eventId ->
                    navController.navigate("${AppScreen.Event.route}/$eventId")
                }
            )
        }

        composable(AppScreen.EventsCalendar.route) {
            val viewModel: EventsCalendarViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            EventsCalendarScreen(
                state = state,
                onAction = viewModel::setAction,
                onMonthClick = { monthNumber ->
                    navController.navigate("${AppScreen.Event.route}/$monthNumber")
                }
            )
        }

        composable(AppScreen.Groups.route) {
            val viewModel: GroupsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            GroupsScreen(
                state = state,
                onAction = viewModel::setAction,
                onGroupClick = {
                    navController.navigate("${AppScreen.GroupItem.route}/$it")
                },
                onAddGroupClick = {
                    navController.navigate("${AppScreen.GroupEdit.route}/${null}")
                }
            )
        }

        composable(AppScreen.Settings.route) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            SettingsScreen(
                state = state,
                onAction = viewModel::setAction,
            )
        }

        composable(
            route = "${AppScreen.GroupItem.route}/{$ARGS_GROUP_ID}",
            arguments = listOf(navArgument(ARGS_GROUP_ID) { type = NavType.LongType })
        ) {
            val viewModel: GroupItemViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            GroupItemScreen(
                groupId = it.arguments?.getLong(ARGS_GROUP_ID),
                state = state,
                onAction = viewModel::setAction,
            )
        }

        composable(
            route = "${AppScreen.GroupEdit.route}/{$ARGS_GROUP_ID}",
            arguments = listOf(navArgument(ARGS_GROUP_ID) { type = NavType.LongType })
        ) {
            val viewModel: GroupEditViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            GroupEditScreen(
                groupId = it.arguments?.getLong(ARGS_GROUP_ID),
                state = state,
                onAction = viewModel::setAction,
                onGroupSaved = { navController.popBackStack() }
            )
        }

        composable(AppScreen.AddEvent.route) {
            AddEventScreen(
                viewModel = hiltViewModel(),
                onEventAdded = { eventId ->
                    navController.navigate("${AppScreen.Event.route}/$eventId") {
                        popUpTo(AppScreen.EventsList.route)
                    }
                }
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
