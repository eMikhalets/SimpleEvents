package com.emikhalets.simpleevents.presentation.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Blue_300 = Color(0xFF64b5f6)
val Grey_900 = Color(0xFF212121)
val Grey_500 = Color(0xFF9e9e9e)
val Grey_200 = Color(0xFFeeeeee)
val Green_600 = Color(0xFF43a047)
val Red_600 = Color(0xFFe53935)

val Colors.backgroundSecondary: Color
    get() = Grey_200

val Colors.onBackgroundSecondary: Color
    get() = if (isLight) Color(0xFF7C7C7C) else Color(0xFF666666)

val Colors.backgroundNegative: Color
    get() = if (isLight) Color(0xFFD32F2F) else Color(0xFF666666)
