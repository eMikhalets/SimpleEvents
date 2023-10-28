package com.emikhalets.simpleevents.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.emikhalets.simpleevents.core.common.InvalidLong

const val ARGS_EVENT_ID: String = "ARGS_EVENT_ID"

val eventDetailsRoute: String
    get() = "${AppRoute.EventDetails}/{$ARGS_EVENT_ID}"

val eventEditRoute: String
    get() = "${AppRoute.EventEdit}/{$ARGS_EVENT_ID}"

val eventIdArgument: NamedNavArgument
    get() = navArgument(ARGS_EVENT_ID) { type = NavType.LongType }

fun eventDetailsRoute(id: Long?): String {
    return "${AppRoute.EventDetails}/$id"
}

fun eventEditRoute(id: Long): String {
    return "${AppRoute.EventEdit}/$id"
}

fun NavBackStackEntry.getEventId(): Long {
    return arguments?.getLong(ARGS_EVENT_ID) ?: InvalidLong
}
