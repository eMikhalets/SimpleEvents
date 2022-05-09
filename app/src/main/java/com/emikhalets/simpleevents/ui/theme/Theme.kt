package com.emikhalets.simpleevents.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Colors.backgroundSecondary: Color
    get() = if (isLight) Color(0xFFEEEEEE) else Color(0xFF666666)

val Colors.onBackgroundSecondary: Color
    get() = if (isLight) Color(0xFF7C7C7C) else Color(0xFF666666)

val Colors.backgroundNegative: Color
    get() = if (isLight) Color(0xFFD32F2F) else Color(0xFF666666)

private val DarkColorPalette = darkColors(
    primary = Color(0xFF666666),
    primaryVariant = Color(0xFF666666),
    secondary = Color(0xFF818181),
    background = Color(0xFF666666),
    surface = Color(0xFFFFFFFF)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFF000000),
    primaryVariant = Color(0xFF000000),
    secondary = Color(0xFF666666),
    background = Color(0xFF6EBEB1),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF)

    /* Other default colors to override
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SimpleEventsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}