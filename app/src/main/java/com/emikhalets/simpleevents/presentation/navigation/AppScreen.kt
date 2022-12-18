package com.emikhalets.simpleevents.presentation.navigation

import androidx.annotation.DrawableRes
import com.emikhalets.simpleevents.R

sealed class AppScreen(val route: String, @DrawableRes val iconRes: Int) {
    object Home : AppScreen("home", R.drawable.ic_round_home_24)
    object AddEvent : AppScreen("add_event", R.drawable.ic_round_add_circle_24_white)
    object Settings : AppScreen("settings", R.drawable.ic_round_settings_24)
    object Event : AppScreen("event", 0)
    object EditEvent : AppScreen("edit_event", 0)
}
