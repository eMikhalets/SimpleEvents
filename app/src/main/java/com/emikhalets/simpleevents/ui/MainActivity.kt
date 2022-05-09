package com.emikhalets.simpleevents.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsNavHost
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsScaffold
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Application() }
    }
}

@Composable
fun Application() {
    val navHost = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    SimpleEventsTheme {
        SimpleEventsScaffold(navHost) {
            SimpleEventsNavHost(
                navController = navHost,
                scaffoldState = scaffoldState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicationPreview() {
    Application()
}