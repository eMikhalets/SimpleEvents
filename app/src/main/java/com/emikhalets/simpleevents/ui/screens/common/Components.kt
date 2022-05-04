package com.emikhalets.simpleevents.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDate
import com.emikhalets.simpleevents.utils.extensions.pluralsResource

@Composable
fun SimpleEventsScaffold(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = { SimpleEventsBottomBar(navController) },
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
    val screens = listOf(
        AppScreen.Home,
        AppScreen.AddEvent,
        AppScreen.Settings
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
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

@Composable
fun EventListItem(event: EventEntity) {
    val date = event.date.formatDate("EE, MM/dd")
    val type = stringResource(event.eventType.nameRes)
    val turns = stringResource(R.string.event_list_item_turns, event.ageTurns)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .padding(8.dp)
    ) {
        RowContainer(background = MaterialTheme.colors.background) {
            Text(
                text = event.daysCount.toString(),
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = pluralsResource(R.plurals.event_list_item_days, event.daysCount),
                color = MaterialTheme.colors.onBackground,
                fontSize = 14.sp,
                letterSpacing = 2.sp
            )
        }
        RowContainer(background = MaterialTheme.colors.backgroundSecondary) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                tint = MaterialTheme.colors.onBackgroundSecondary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = event.name,
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = if (event.ageTurns == 0) "$date • $type" else "$date • $type • $turns",
                color = MaterialTheme.colors.secondary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun RowContainer(background: Color, content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(background)
            .fillMaxHeight()
            .aspectRatio(1f)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEventListItem() {
    SimpleEventsTheme {
        EventListItem(
            event = EventEntity(
                id = 0,
                daysCount = 6,
                ageTurns = 42,
                name = "Test Full Name",
                date = System.currentTimeMillis(),
                eventType = EventType.BIRTHDAY,
                note = "Some note text"
            )
        )
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.textFieldColors(
            leadingIconColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.backgroundSecondary,
            placeholderColor = MaterialTheme.colors.onBackgroundSecondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}