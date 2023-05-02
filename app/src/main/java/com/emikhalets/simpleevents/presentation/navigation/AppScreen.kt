package com.emikhalets.simpleevents.presentation.navigation

import androidx.annotation.DrawableRes
import com.emikhalets.simpleevents.R

sealed class AppScreen(val route: String, @DrawableRes val iconRes: Int) {

    object EventsList : AppScreen("events_list", R.drawable.ic_baseline_format_list_bulleted_24)

    object EventsCalendar : AppScreen("events_calendar", R.drawable.ic_baseline_calendar_month_24)

    object Groups : AppScreen("groups", R.drawable.ic_baseline_group_24)

    object Settings : AppScreen("settings", R.drawable.ic_baseline_settings_24)

    object GroupItem : AppScreen("group_item", 0)

    object GroupEdit : AppScreen("group_edit", 0)

    object Event : AppScreen("event", 0)

    object AddEvent : AppScreen("add_event", R.drawable.ic_round_add_circle_24_white)

    object EditEvent : AppScreen("edit_event", 0)
}
