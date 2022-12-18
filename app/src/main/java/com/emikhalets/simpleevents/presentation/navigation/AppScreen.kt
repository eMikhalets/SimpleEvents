package com.emikhalets.simpleevents.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreen(val route: String, val icon: ImageVector) {
    object Home : AppScreen("home", Icons.Default.Home)
    object AddEvent : AppScreen("add_event", Icons.Default.AddCircle)
    object Settings : AppScreen("settings", Icons.Default.Settings)
    object Event : AppScreen("event", Icons.Default.Build)
    object EditEvent : AppScreen("edit_event", Icons.Default.Edit)
}
