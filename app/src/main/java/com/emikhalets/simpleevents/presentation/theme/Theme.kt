package com.emikhalets.simpleevents.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = AppColors.Primary,
    primaryVariant = AppColors.Primary,
    background = AppColors.BackgroundPrimary,
    surface = AppColors.Surface,
    error = AppColors.Red_600,
    onPrimary = Color.White,
    onBackground = AppColors.TextPrimary,
    onSurface = AppColors.TextPrimary,
    onSecondary = AppColors.TextSecondary
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
