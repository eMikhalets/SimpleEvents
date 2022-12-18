package com.emikhalets.simpleevents.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Blue_300,
    primaryVariant = Blue_300,
    secondary = Grey_500,
    background = Color.White,
    surface = Color.White,
    error = Red_600,
    onPrimary = Grey_900,
    onBackground = Grey_900,
    onSurface = Grey_900
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
