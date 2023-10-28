package com.emikhalets.simpleevents.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorPalette = darkColors(
    primary = Color(0xFFBB86FC),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    secondaryVariant = Color(0xFF03DAC6),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)
private val lightColorPalette = lightColors(
    primary = Color.Purple500,
    primaryVariant = Color.Purple700,
    secondary = Color.Teal200,
    secondaryVariant = Color.Teal700,
    background = Color.White,
    surface = Color.White,
    error = Color.Red,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) darkColorPalette else lightColorPalette
    Surface(color = MaterialTheme.colors.surface) {
        MaterialTheme(
            colors = lightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
