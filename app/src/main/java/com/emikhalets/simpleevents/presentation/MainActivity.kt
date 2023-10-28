package com.emikhalets.simpleevents.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.compose.rememberNavController
import com.emikhalets.simpleevents.presentation.components.AppScaffold
import com.emikhalets.simpleevents.presentation.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            AppScaffold(
                navController = navController,
                scaffoldState = scaffoldState
            ) {
                AppNavHost(navController = navController)
            }
        }
    }
}
