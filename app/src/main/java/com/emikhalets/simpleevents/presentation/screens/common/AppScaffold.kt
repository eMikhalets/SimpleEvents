package com.emikhalets.simpleevents.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.simpleevents.presentation.navigation.AppScreen


@Composable
fun SimpleEventsScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = { SimpleEventsBottomBar(navController) },
        scaffoldState = scaffoldState,
        content = {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.surface)
                    .padding(it),
                content = { content() }
            )
        }
    )
}

@Composable
private fun SimpleEventsBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = remember { listOf(AppScreen.Home, AppScreen.AddEvent, AppScreen.Settings) }

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    if (screen.route == AppScreen.AddEvent.route) {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier.size(48.dp)
                        )
                    } else {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null
                        )
                    }
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.secondary,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}