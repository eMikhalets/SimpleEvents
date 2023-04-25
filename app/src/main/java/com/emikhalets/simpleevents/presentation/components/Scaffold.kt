package com.emikhalets.simpleevents.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.simpleevents.presentation.navigation.AppScreen
import com.emikhalets.simpleevents.presentation.theme.AppTheme

@Composable
fun AppScaffold(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    content: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.surface,
        bottomBar = { AppBottomBar(navController) },
        content = { Box(modifier = Modifier.padding(it), content = { content() }) }
    )
}

@Composable
private fun AppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = remember {
        listOf(
            AppScreen.EventsList,
            AppScreen.EventsCalendar,
            AppScreen.Groups,
            AppScreen.Settings
        )
    }

    BottomNavigation {
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    if (screen.route == AppScreen.AddEvent.route) {
                        AppIcon(drawableRes = screen.iconRes, modifier = Modifier.size(48.dp))
                    } else {
                        AppIcon(drawableRes = screen.iconRes)
                    }
                },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onSecondary,
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        AppBottomBar(rememberNavController())
    }
}
