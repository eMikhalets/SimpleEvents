package com.emikhalets.simpleevents.presentation.navigation

import androidx.navigation.NavHostController

const val ARGS_EVENT_ID = "app_args_event_id"

fun getBottomScreens(): List<AppScreen> {
    return listOf(AppScreen.Home, AppScreen.AddEvent, AppScreen.Settings)
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
