package com.emikhalets.simpleevents.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.emikhalets.simpleevents.presentation.navigation.AppNavHost
import com.emikhalets.simpleevents.presentation.components.AppScaffold
import com.emikhalets.simpleevents.presentation.theme.AppTheme
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

    AppTheme {
        AppScaffold(
            navController = navHost,
            scaffoldState = scaffoldState
        ) {
            AppNavHost(
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